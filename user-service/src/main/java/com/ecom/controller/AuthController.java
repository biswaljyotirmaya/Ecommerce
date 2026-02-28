package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.DTOs.AuthRequest;
import com.ecom.DTOs.UserContactDTO;
import com.ecom.entity.AuthResponse;
import com.ecom.entity.User;
import com.ecom.service.AuthService;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        return new AuthResponse(token);
    }
    
    @GetMapping("/contact/{id}")
    public UserContactDTO getUserContact(@PathVariable Long id) {
        return authService.getUserContactByID(id);
    }

}
