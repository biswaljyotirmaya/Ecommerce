package com.ecom.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecom.dto.ProductDTO;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

	@GetMapping("/api/products/{id}")
	ProductDTO getProduct(@PathVariable Long id);
}
