package com.ecom.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.config.FeignConfig;

@FeignClient(name = "INVENTORY-SERVICE", configuration = FeignConfig.class)
public interface InventoryClient {

	@PutMapping("/api/inventory/reduce")
	void reduce(@RequestParam Long productId, @RequestParam int qty);
}
