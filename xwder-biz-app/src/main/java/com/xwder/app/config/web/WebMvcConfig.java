package com.xwder.app.config.web;

import com.xwder.app.interceptor.GlobalInterceptor;
import com.xwder.app.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author xwder
 * @Description: web
 * @date 2020/7/11 19:12
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    @Autowired
    private GlobalInterceptor globalInterceptor;

    /**
     * 注册类路径下的static和templates文件夹
     * 重写addResourceHandlers方法
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns(
                        "/user/**",
                        "/book/**",
                        "/file/**",
                        "/video/**",
                        "/blog/edit/**")
                .excludePathPatterns("/static/**",
                        "/doLoginOut",
                        "/user/mail/verifyMail.html");

        // 拦截所有的请求 导航栏分类数据
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**","/error");

        super.addInterceptors(registry);
    }

}
