package io.renren; /**
 * @author gaoxipeng
 * @Date 2018/9/6 13:40
 * @Description 调取高德地图api，可以取经纬度，可以计算两点距离，可以查询公交线路
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 1.客户申请个高德API上面的key
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BusRouteTest {
    private Logger logger = LoggerFactory.getLogger(BusRouteTest.class);
    @Value("${us.bus.key}")
    private String key;

    @Value("${us.bus.output}")
    private String output;

    @Value("${us.bus.city}")
    private String city;

    @Value("${us.bus.geocodeUrl}")
    private String geocodeUrl;

    @Test
    public  void busDemo(){
        String start = "二井镇";
        String end = "大庆西站";

        String startLonLat = getLonLat(start);
        String endLonLat = getLonLat(end);

        System.out.println("起始地："+start+",经纬度："+startLonLat);
        System.out.println("终点："+end+",经纬度："+endLonLat);
        //公交线路
        String queryUrl ="https://restapi.amap.com/v3/direction/transit/integrated?origin="+startLonLat+"&destination="+endLonLat+"&city="+city+"&strategy=0&output="+output+"&key="+key+"&date=2014-3-19&time=22:34";
        logger.info("请求地址:"+queryUrl);
        String response = getResponse(queryUrl);
        System.out.println("公交线路:"+ R.ok(response));
        Long dis = getDistance(startLonLat,endLonLat);
        System.out.println("两点间距离："+dis+"米");
    }
    /**
     * 0.得到两个地址间距离
     * */
    public long getDistanceByAdress(String start, String end){
        String startLonLat = getLonLat(start);
        String endLonLat = getLonLat(end);
        long dis = getDistance(startLonLat,endLonLat);
        return dis;
    }

    /**
     * 1.地址转换为经纬度
     * */
    public  String getLonLat(String address){
        //返回输入地址address的经纬度信息, 格式是 经度,纬度
        //String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=50a751269e0cdc535921a65e8705a292&address="+address;
        String queryUrl = geocodeUrl+"key="+key+"&city="+city+"&address="+address;
        System.out.println("发送的url:"+queryUrl);
        String queryResult = getResponse(queryUrl);  //高德接口返回的是JSON格式的字符串
        System.out.println("gao:"+queryResult);
        JSONObject job = JSONObject.parseObject(queryResult);
        JSONObject jobJSON = JSONObject.parseObject(job.get("geocodes").toString().substring(1, job.get("geocodes").toString().length()-1));
        String DZ = jobJSON.get("location").toString();
        System.out.println("经纬度："+DZ);
        return DZ;
    }

    /**
     * 2.经纬度算出两点间距离
     * */
    private  long getDistance(String startLonLat, String endLonLat){
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = new Long(0);
        String queryUrl = "http://restapi.amap.com/v3/distance?key=50a751269e0cdc535921a65e8705a292&city="+city+"&origins="+startLonLat+"&destination="+endLonLat;
        String queryResult = getResponse(queryUrl);
        JSONObject job = JSONObject.parseObject(queryResult);
        JSONArray ja = job.getJSONArray("results");
        JSONObject jobO = JSONObject.parseObject(ja.getString(0));
        result = Long.parseLong(jobO.get("distance").toString());
        System.out.println("距离2："+result);
        return result;
    }

    /**
     * 3.发送请求
     * */
    private  String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}

