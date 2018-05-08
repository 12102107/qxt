package io.renren.config;

import io.renren.common.xss.XssFilter;
import io.renren.modules.us.interceptor.PermissionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;

/**
 * Filter配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-21 21:56
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

//    @Bean
//    public FilterRegistrationBean permissionFilterRegistration() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        //注入过滤器
//        filterRegistrationBean.setFilter(new PermissionFilter());
//        //拦截规则
//        filterRegistrationBean.addUrlPatterns("/api/**");
//        //过滤器名称
//        filterRegistrationBean.setName("permissionFilter");
//        //是否自动注册 false 取消Filter的自动注册
//        filterRegistrationBean.setEnabled(false);
//        //过滤器顺序
//        filterRegistrationBean.setOrder(1);
//        return filterRegistrationBean;
//    }

}
