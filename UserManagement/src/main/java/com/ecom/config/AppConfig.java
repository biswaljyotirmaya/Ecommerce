package com.ecom.config;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AppConfig {

	private Map<String,String> msg;
}
