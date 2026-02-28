package com.ecom.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.dto.CartItemDTO;
import com.ecom.dto.UserContactDTO;
import com.ecom.entity.CheckoutSession;
import com.ecom.entity.CheckoutStatus;
import com.ecom.feign.CartClient;
import com.ecom.feign.UserClient;
import com.ecom.repository.CheckoutSessionRepository;

@Service
public class CheckoutService {

	private final CheckoutSessionRepository repository;
	private final UserClient userClient;
	private final CartClient cartClient;

	public CheckoutService(CheckoutSessionRepository repository, UserClient userClient, CartClient cartClient) {
		this.repository = repository;
		this.userClient = userClient;
		this.cartClient = cartClient;
	}

	@Transactional
	public String startCheckout(Long userId) {

		// 1️⃣ Fetch cart
		List<CartItemDTO> cartItems = cartClient.getCart();

		if (cartItems == null || cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		// 2️⃣ Validate single vendor
		Long vendorId = cartItems.get(0).getVendorId();

		if (vendorId == null) {
			throw new RuntimeException("Invalid cart state: vendor missing");
		}

		for (CartItemDTO item : cartItems) {
			if (item.getVendorId() == null || !item.getVendorId().equals(vendorId)) {
				throw new RuntimeException("Multi-vendor or corrupted cart");
			}
		}

		// 3️⃣ Calculate total
		double totalAmount = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

		// 4️⃣ Generate OTP
		String otp = generateOtp();

		// 5️⃣ Save checkout session
		// Enforce single active session
		repository.deleteByUserId(userId);

		// Create new session
		CheckoutSession session = new CheckoutSession();
		session.setUserId(userId);
		session.setVendorId(vendorId);
		session.setTotalAmount(totalAmount);
		session.setOtp(otp);
		session.setStatus(CheckoutStatus.OTP_SENT);
		session.setCreatedAt(LocalDateTime.now());

		session.setExpiresAt(LocalDateTime.now().plusMinutes(5));
		session.setVerified(false);

		repository.save(session);

		// 6️⃣ Resolve contact
		UserContactDTO user = userClient.getUserContact(userId);

		if (user.getEmail() != null) {
			System.out.println("OTP " + otp + " sent to EMAIL: " + user.getEmail());
		} else {
			System.out.println("OTP " + otp + " sent to MOBILE: " + user.getMobile());
		}

		return "OTP sent to registered contact";
	}

	private String generateOtp() {
		return String.valueOf((int) (Math.random() * 900000) + 100000);
	}

	public String verifyOtp(Long userId, String otp) {

		CheckoutSession session = repository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("No active checkout session"));

		if (session.isVerified()) {
			throw new RuntimeException("OTP already verified");
		}

		if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("OTP expired");
		}

		if (!session.getOtp().equals(otp)) {
			throw new RuntimeException("Invalid OTP");
		}

		session.setVerified(true);
		session.setStatus(CheckoutStatus.VERIFIED);
		repository.save(session);

		return "OTP verified. Order can be placed.";
	}

}
