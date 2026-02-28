package com.ecom.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ecom.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String path = exchange.getRequest().getURI().getPath();

		// 1️⃣ Always allow CORS preflight
		if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
			return chain.filter(exchange);
		}

		// 2️⃣ Public APIs (NO JWT)
		if (path.equals("/api/users/login") || path.startsWith("/api/products/") // ✅ ADD THIS
				|| path.equals("/api/products") || path.equals("/api/users/register") || path.startsWith("/actuator")
				|| path.startsWith("/eureka")) {

			return chain.filter(exchange);
		}

		// 3️⃣ JWT REQUIRED for everything else
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return unauthorized(exchange);
		}

		String token = authHeader.substring(7);

		// 4️⃣ Validate token
		if (!jwtUtil.validateToken(token)) {
			return unauthorized(exchange);
		}

		// 5️⃣ Extract identity
		String userId = jwtUtil.extractUsername(token);
		String role = jwtUtil.extractRole(token);

		System.out.println("GW -> X-USER-ID = " + userId);
		System.out.println("GW -> X-USER-ROLE = " + role);

		if (!role.startsWith("ROLE_")) {
			role = "ROLE_" + role;
		}

		// 6️⃣ Forward headers to downstream services
		ServerWebExchange modifiedExchange = exchange.mutate()
				.request(exchange.getRequest().mutate().header("X-USER-ID", userId).header("X-USER-ROLE", role).build())
				.build();

		return chain.filter(modifiedExchange);
	}

	private Mono<Void> unauthorized(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}

	@Override
	public int getOrder() {
		return -1; // run before security filters
	}
}
