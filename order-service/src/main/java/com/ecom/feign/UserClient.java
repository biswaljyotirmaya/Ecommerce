package com.ecom.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecom.dto.UserContactDTO;

@FeignClient(name = "user-service")
public interface UserClient {

	@GetMapping("/api/users/contact/{id}")
	UserContactDTO getUserContact(@PathVariable Long id);
}
