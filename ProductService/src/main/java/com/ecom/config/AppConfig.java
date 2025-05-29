package com.ecom.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "product.module")
@EnableConfigurationProperties
@Data
public class AppConfig {
private Map<String,String> message;
}
