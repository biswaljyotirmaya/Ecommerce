package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
