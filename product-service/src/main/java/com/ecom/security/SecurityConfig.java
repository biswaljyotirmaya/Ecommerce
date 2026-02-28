package com.ecom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // harmless even if unused
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, HeaderAuthenticationFilter headerAuthenticationFilter)
			throws Exception {

		http.csrf(csrf -> csrf.disable()).formLogin(form -> form.disable()).httpBasic(basic -> basic.disable())
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.addFilterBefore(headerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
