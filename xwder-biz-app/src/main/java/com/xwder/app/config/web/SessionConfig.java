package com.xwder.app.config.web;

import com.alibaba.fastjson.JSON;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.cloud.commmon.api.ResultCode;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//@Configuration

/**
 *
 */
public class SessionConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor())
                //排除拦截
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/**")
                //拦截路径
                .addPathPatterns("/user/**");
    }


    @Configuration
    public class SecurityInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();
            // TODO 获取用户信息
            if (session.getAttribute(session.getId()) != null) {
                return true;
            }
            response.getWriter().write(JSON.toJSONString(
                    CommonResult.failed(ResultCode.FORBIDDEN.getCode(), "please login first")
            ));
            return false;
        }
    }
}