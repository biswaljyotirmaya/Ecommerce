package com.ecom.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "checkout_session")
@Getter
@Setter
public class CheckoutSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;
	private Long vendorId;
	private double totalAmount;

	private String otp;

	@Enumerated(EnumType.STRING)
	private CheckoutStatus status;

	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	private boolean verified;
}
