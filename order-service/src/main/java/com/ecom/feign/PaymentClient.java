package com.ecom.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
	@PostMapping("/api/payments/pay")
	String makePayment(@RequestParam Long orderId, @RequestParam double amount);
}
