package io.renren.modules.us.interceptor;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.SpringContextUtils;
import io.renren.modules.us.entity.UsApiEntity;
import io.renren.modules.us.service.UsApiService;
import io.renren.modules.us.util.UsSessionUtil;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.SessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author Li
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private UsSessionUtil sessionUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept");
            return true;
        }
        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        String url = request.getServletPath();
        logger.info("访问URL" + url);
        logger.info("访问参数" + requestBody);
        JSONObject object = JSONObject.parseObject(requestBody);
        UsApiService apiService = (UsApiService) SpringContextUtils.getBean("usApiService");
        if (object == null || !object.containsKey("appid")) {
            UsApiEntity api = apiService.getWithoutAppId(url);
            if (api == null) {
                throw new AuthorizationException();
            } else {
                return super.preHandle(request, response, handler);
            }
        } else {
            String appId = object.getString("appid");
            UsApiEntity api = apiService.getWithAppId(appId, url);
            //没有权限
            if (api == null) {
                throw new AuthorizationException();
            }
            //有权限,请求参数没有session
            if (!object.containsKey("session")) {
                return super.preHandle(request, response, handler);
            }
            //有权限,请求参数有session
            String session = object.getString("session");
            String userId = sessionUtil.getUserId(session);
            if (userId == null) {
                throw new SessionException();
            } else {
                return super.preHandle(request, response, handler);
            }
        }
    }

    @Autowired
    public void setSessionUtil(UsSessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

}