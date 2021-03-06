package com.xwder.app.config.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwder.app.interceptor.GlobalInterceptor;
import com.xwder.app.interceptor.NovelInterceptor;
import com.xwder.app.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

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

    @Autowired
    private NovelInterceptor novelInterceptor;

    /**
     * 注册类路径下的static和templates文件夹
     * 重写addResourceHandlers方法
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/sysstatic/**").addResourceLocations("classpath:/sysstatic/");
        super.addResourceHandlers(registry);
    }

    /**
     * 注意拦截器的添加顺序 为 执行顺序
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        // 拦截所有的请求 导航栏分类数据
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/error");

        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns(
                        // 测试接口使用
                        "/test/**",
                        "/user/**",
                        "/book/**",
                        "/file/**",
                        "/video/**",
                        "/blog/edit/**",
                        "/sys/**")
                .excludePathPatterns("/static/**",
                        "/doLoginOut",
                        "/mail/verifyMail.html",
                        "/login/**");

        registry.addInterceptor(novelInterceptor)
                .addPathPatterns("/book/**","/sys/**")
                .excludePathPatterns();

        super.addInterceptors(registry);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        /**
         * 日期全局格式化
         * */
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
        super.configureMessageConverters(converters);
    }
}
