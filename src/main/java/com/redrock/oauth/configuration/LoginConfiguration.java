package com.redrock.oauth.configuration;

import com.redrock.oauth.intercpter.IntercpterLogin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    /**添加拦截器*/
    @Bean
    public IntercpterLogin loginInterceptor(){
        return new IntercpterLogin();
    }

    /**
     * 配置请求拦截器
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> interceptorSet = new ArrayList<>();
        //本服务
        interceptorSet.add("/login");
        interceptorSet.add("/registe");
        List<String> passSet = new ArrayList<>();
        //第三方授权
        passSet.add("/user/**");
        passSet.add("/redirectlogin");

        //拦截所有请求
        registry.addInterceptor(loginInterceptor()).addPathPatterns(interceptorSet).excludePathPatterns(passSet);

    }

    /**
     * 方法级别的单个参数验证开启
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}