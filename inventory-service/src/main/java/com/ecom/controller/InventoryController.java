package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryService service;

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_VENDOR')")
	@PostMapping("/add")
	public void addStock(@RequestParam Long productId, @RequestParam int qty) {
		service.addStock(productId, qty);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_CONSUMER','ROLE_VENDOR','ROLE_ADMIN')")
	@PutMapping("/reduce")
	public void reduce(@RequestParam Long productId, @RequestParam int qty) {
		service.reduceStock(productId, qty);
	}

}
