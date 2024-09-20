package com.xblteam.lease.common.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "aliyun.sms")
@ConditionalOnProperty(name = "aliyun.sms.endpoint")
@Configuration
public class AliyunSMSConfiguration {

    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;

    @Bean
    public Client client() {
        Config config = new Config();                   //创建配置
        config.setAccessKeyId(accessKeyId);
        config.setAccessKeySecret(accessKeySecret);
        config.setEndpoint(endpoint);
        try {
            return new Client(config);                  //创建客户端
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}