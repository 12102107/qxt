package io.renren.modules.us.interceptor;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.us.service.UsAppApiService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.AuthorizationException;
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

    private UsAppApiService appApiService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        JSONObject object = JSONObject.parseObject(requestBody);
        if (!object.containsKey("appid")) {
            throw new AuthorizationException();
        }
        String appid = object.getString("appid");
        String url = request.getServletPath();
        Integer i = appApiService.countId(appid, url);
        if (i == null || i != 1) {
            throw new AuthorizationException();
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    @Autowired
    public void setAppApiService(UsAppApiService appApiService) {
        this.appApiService = appApiService;
    }
}
