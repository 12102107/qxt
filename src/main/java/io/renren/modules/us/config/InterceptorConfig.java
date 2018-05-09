package io.renren.modules.us.config;

import io.renren.modules.us.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Li
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/api/**");
    }

    @Autowired
    public void setPermissionInterceptor(PermissionInterceptor permissionInterceptor) {
        this.permissionInterceptor = permissionInterceptor;
    }
    
}
