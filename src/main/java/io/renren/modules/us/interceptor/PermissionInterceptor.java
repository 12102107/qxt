package io.renren.modules.us.interceptor;

import io.renren.common.utils.HttpContextUtils;
import io.renren.modules.us.param.UsBaseParam;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author Li
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestBody= IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
//        String u1 = request.getRequestURI();
//        String u2 = request.getServletPath();
//        Object o = request.getAttribute("appid");
//        String s = request.getParameter("appid");
//        if (handler instanceof UsBaseParam) {
//            System.out.println(1);
//        }
//        Object o1=HttpContextUtils.getHttpServletRequest().getParameter("appid");
//        HandlerMethod method = (HandlerMethod) handler;
//        MethodParameter[] parameters = method.getMethodParameters();
//        for (MethodParameter parameter : parameters){
//        }
        return super.preHandle(request, response, handler);
    }
}
