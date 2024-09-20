package com.xblteam.lease.web.admin.custom.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * knife4j文档配置类
 */
@Configuration
public class Knife4jConfiguration {

    /**
     * knife4j接口文档基本信息
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("后台管理系统API")
                        .version("1.0")
                        .description("后台管理系统API"));
    }

    /**
     * 系统信息接口分组
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi systemAPI() {
        return GroupedOpenApi.builder().group("系统信息管理").
                pathsToMatch(
                        "/admin/system/**"
                ).build();
    }

    /**
     * 后台登录接口分组
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi loginAPI() {
        return GroupedOpenApi.builder().group("后台登录管理").
                pathsToMatch(
                        "/admin/login/**",
                        "/admin/info"
                ).build();
    }

    /**
     * 公寓信息接口分组
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi apartmentAPI() {
        return GroupedOpenApi.builder().group("公寓信息管理").
                pathsToMatch(
                        "/admin/apartment/**",
                        "/admin/room/**",
                        "/admin/label/**",
                        "/admin/facility/**",
                        "/admin/fee/**",
                        "/admin/attr/**",
                        "/admin/payment/**",
                        "/admin/region/**",
                        "/admin/term/**",
                        "/admin/file/**"
                ).build();
    }

    /**
     * 租赁信息接口分组
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi leaseAPI() {
        return GroupedOpenApi.builder().group("租赁信息管理").
                pathsToMatch(
                        "/admin/appointment/**",
                        "/admin/agreement/**"
                ).build();
    }

    /**
     * 平台用户接口分组
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi userAPI() {
        return GroupedOpenApi.builder().group("平台用户管理").
                pathsToMatch(
                        "/admin/user/**"
                ).build();
    }
}
