package com.example.AuthorizatioDemo.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {
    private String secret;
    private long expiration;
}
