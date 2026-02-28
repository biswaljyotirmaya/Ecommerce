package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecom.DTOs.AuthRequest;
import com.ecom.DTOs.UserContactDTO;
import com.ecom.entity.Role;
import com.ecom.entity.User;
import com.ecom.jwt.JwtTokenProvider;
import com.ecom.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String register(User user) {

		if (user.getRole() == null) {
			user.setRole(Role.ROLE_CONSUMER); // default
		}

		if (user.getRole() == Role.ROLE_ADMIN) {
			throw new RuntimeException("Admin registration is not allowed");
		}

		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		return "User registered successfully";
	}

	@Override
	public String login(AuthRequest request) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = repo.findByEmail(authentication.getName())
				.orElseThrow(() -> new RuntimeException("User not found"));

		return jwtTokenProvider.generateToken(user.getId().toString(), user.getRole().name());

	}
	
	@Override
	public UserContactDTO getUserContactByID(@PathVariable Long id) {
        User user = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        UserContactDTO dto = new UserContactDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setMobile(user.getMobile());

        return dto;
    }

}
