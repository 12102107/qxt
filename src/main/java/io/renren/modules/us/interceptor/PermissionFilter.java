package io.renren.modules.us.interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Li
 */
@Component
public class PermissionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        //不需要执行自定义业务逻辑
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            request = new PermissionHttpServletRequestWrapper((HttpServletRequest) request);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //不需要执行自定义业务逻辑
    }

}
