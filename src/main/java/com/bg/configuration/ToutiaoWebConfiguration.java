package com.bg.configuration;


import com.bg.interceptor.LoginRequiredInterceptor;
import com.bg.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2016/7/6.
 */
@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter{


    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor)
                .addPathPatterns("/msg/*").addPathPatterns("/like").addPathPatterns("/dislike");
        super.addInterceptors(registry);
    }
}
