package com.ecom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignSecurityInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			template.header("X-USER-ID", auth.getName());

			auth.getAuthorities().forEach(authority -> template.header("X-USER-ROLE", authority.getAuthority()));
		}
	}
}
