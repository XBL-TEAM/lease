package com.xblteam.lease.web.admin.custom.config;

import com.xblteam.lease.web.admin.Intercptor.AuthenticationInterceptor;
import com.xblteam.lease.web.admin.custom.converter.StringToBaseEnmuConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringMvc配置类
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private StringToBaseEnmuConverterFactory stringToBaseEnmuConverterFactory;
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Autowired
    private CorsConfiguration corsConfiguration;

    /**
     * 注册类型转换器工厂
     *
     * @param registry 注册器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(stringToBaseEnmuConverterFactory);
    }

    /**
     * 注册token校验拦截器
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/admin/**")                   //拦截指定路径
                .excludePathPatterns("/admin/login/**");        //排除指定路径
    }

    /**
     * 全局跨域配置
     * @return 注册器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }
}
