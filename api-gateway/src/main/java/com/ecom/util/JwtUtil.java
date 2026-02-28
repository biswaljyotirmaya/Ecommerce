package com.ecom.util;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET = "mysecretkeymysecretkeymysecretkey123";
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

	// ================= GENERATE TOKEN =================
	public String generateToken(String username, String role) {
		return Jwts.builder().subject(username).claim("role", role).signWith(key).compact();
	}

	// ================= VALIDATE TOKEN =================
	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// ================= READ DATA =================
	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public String extractRole(String token) {
		return getClaims(token).get("role", String.class);
	}

	// ================= INTERNAL =================
	private Claims getClaims(String token) {
		return Jwts.parser().verifyWith(key) // REQUIRED in 0.13.x
				.build().parseSignedClaims(token) // REQUIRED in 0.13.x
				.getPayload();
	}
}
