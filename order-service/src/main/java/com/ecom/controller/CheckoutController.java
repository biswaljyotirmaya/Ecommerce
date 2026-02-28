package com.ecom.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.OtpVerifyRequest;
import com.ecom.service.CheckoutService;
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService service;

    public CheckoutController(CheckoutService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CONSUMER')")
    public String checkout() {

        Long userId = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        return service.startCheckout(userId);
    }
    
    @PostMapping("/verify")
    @PreAuthorize("hasAuthority('ROLE_CONSUMER')")
    public String verifyOtp(@RequestBody OtpVerifyRequest request) {
        Long userId = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );
        return service.verifyOtp(userId, request.getOtp());
    }

}
