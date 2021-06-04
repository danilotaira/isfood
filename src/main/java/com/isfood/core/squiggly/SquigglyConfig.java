package com.isfood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
//        Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));

        List<String> urlPatterns = Arrays.asList("/order/*", "/restaurants/*");

        FilterRegistrationBean<SquigglyRequestFilter> filterRegistrationBean = new FilterRegistrationBean<SquigglyRequestFilter>();
        filterRegistrationBean.setFilter(new SquigglyRequestFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setUrlPatterns(urlPatterns);

        return filterRegistrationBean;
    }
}
