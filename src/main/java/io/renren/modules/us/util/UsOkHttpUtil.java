package io.renren.modules.us.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author fxjzzyo
 * @date 2017/7/12
 */
@Component
public class UsOkHttpUtil {

    private static UsOkHttpUtil okHttpUtil;
    private OkHttpClient okHttpClient;
    private Logger logger = LoggerFactory.getLogger(UsOkHttpUtil.class);

    private UsOkHttpUtil() {
        OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
        //读取超时
        clientBuilder.readTimeout(20, TimeUnit.SECONDS);
        //连接超时
        clientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        //写入超时
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);
        //支持HTTPS请求,跳过证书验证
        try {
            clientBuilder.sslSocketFactory(createSSLSocketFactory(), new X509TrustAllCertsManager());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        clientBuilder.hostnameVerifier((requestedHost, remoteServerSession) -> requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost()));
        okHttpClient = clientBuilder.build();
    }

    /**
     * 单例模式获取OkHttpUtil
     */
    public static UsOkHttpUtil getInstance() {
        if (okHttpUtil == null) {
            okHttpUtil = new UsOkHttpUtil();
        }
        return okHttpUtil;
    }

    /**
     * 生成安全套接字工厂,用于https请求的证书跳过
     */
    private SSLSocketFactory createSSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, new TrustManager[]{new X509TrustAllCertsManager()}, new SecureRandom());
        return sc.getSocketFactory();
    }

    /**
     * post的请求参数,构造RequestBody
     *
     * @param bodyParams 请求参数
     * @return RequestBody
     */
    private RequestBody setRequestBody(Map<String, String> bodyParams) {
        RequestBody body;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (bodyParams != null) {
            Iterator<String> iterator = bodyParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                formEncodingBuilder.add(key, bodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;

    }

    /**
     * get请求,同步方式,获取网络数据,是在主线程中执行的,需要新起线程,将其放到子线程中执行
     *
     * @param url 请求url
     * @return Response
     */
    public Response getDataSync(String url) throws IOException {
        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        //将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //执行Call,得到response
        return call.execute();
    }

    /**
     * post请求,同步方式,提交数据,是在主线程中执行的,需要新起线程,将其放到子线程中执行
     *
     * @param url        请求url
     * @param bodyParams 请求参数
     * @return Response
     */
    public Response postDataSync(String url, Map<String, String> bodyParams) {
        //构造RequestBody
        RequestBody body = setRequestBody(bodyParams);
        //构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        //将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //执行Call,得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return response;
    }

    /**
     * get请求,异步方式,获取网络数据,是在子线程中执行的,需要切换到主线程才能更新UI
     *
     * @param url       请求url
     * @param myNetCall myNetCall
     */
    public void getDataAsync(String url, final MyNetCall myNetCall) {
        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        //将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myNetCall.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myNetCall.success(call, response);
            }
        });
    }

    /**
     * post请求,异步方式,提交数据,是在子线程中执行的,需要切换到主线程才能更新UI
     *
     * @param url        请求url
     * @param bodyParams 请求参数
     * @param myNetCall  myNetCall
     */
    public void postDataAsync(String url, Map<String, String> bodyParams, final MyNetCall myNetCall) {
        //1构造RequestBody
        RequestBody body = setRequestBody(bodyParams);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        //3 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myNetCall.failed(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                myNetCall.success(call, response);
            }
        });
    }

    /**
     * 自定义网络回调接口
     */
    public interface MyNetCall {
        void success(Call call, Response response) throws IOException;

        void failed(Call call, IOException e);
    }

    /**
     * 用于信任所有证书
     */
    class X509TrustAllCertsManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            //信任所有证书
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            //信任所有证书
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
