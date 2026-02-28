package com.ecom.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private static final String SECRET = "mysecretkeymysecretkeymysecretkey123";
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

	private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

	public String generateToken(String userId, String role) {

		return Jwts.builder().subject(userId).claim("role", role).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(key).compact();
	}
}
