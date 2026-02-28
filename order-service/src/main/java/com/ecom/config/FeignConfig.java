package com.ecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {

			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

			if (attrs == null)
				return;

			HttpServletRequest request = attrs.getRequest();

			String userId = request.getHeader("X-USER-ID");
			String role = request.getHeader("X-USER-ROLE");

			if (userId != null) {
				requestTemplate.header("X-USER-ID", userId);
			}
			if (role != null) {
				requestTemplate.header("X-USER-ROLE", role);
			}
		};
	}
}
