/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedHeaders(CorsConfiguration.ALL)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(3600);
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration corsConfiguration = new CorsConfiguration();
//        /*是否允许请求带有验证信息*/
//        corsConfiguration.setAllowCredentials(true);
//        /*允许访问的客户端域名*/
//        corsConfiguration.addAllowedOrigin("*");
//        /*允许服务端访问的客户端请求头*/
//        corsConfiguration.addAllowedHeader("*");
//        /*允许访问的方法名,GET POST等*/
//        corsConfiguration.addAllowedMethod("*");
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
}