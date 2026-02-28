package com.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.CheckoutSession;

public interface CheckoutSessionRepository extends JpaRepository<CheckoutSession, Long> {

	void deleteByUserId(Long userId);

	Optional<CheckoutSession> findByUserId(Long userId);

}
