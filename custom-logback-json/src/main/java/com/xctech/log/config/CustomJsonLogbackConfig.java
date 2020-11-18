package com.xctech.log.config;

import com.xctech.log.filter.CustomMDCInsertingServletFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 应用配置
 * @author: bo.yan
 * @date: 2020/11/13 下午5:32
 */
@Configuration
@ConditionalOnClass(Filter.class)
public class CustomJsonLogbackConfig {
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        Filter actionFilter  = new CustomMDCInsertingServletFilter();
        registrationBean.setFilter(actionFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}