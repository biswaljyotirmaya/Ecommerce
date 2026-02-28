package com.ecom.service;

import org.springframework.web.bind.annotation.PathVariable;

import com.ecom.DTOs.AuthRequest;
import com.ecom.DTOs.UserContactDTO;
import com.ecom.entity.User;

public interface AuthService {
	String register(User user);

	String login(AuthRequest request);

	UserContactDTO getUserContactByID(@PathVariable Long id);
}
