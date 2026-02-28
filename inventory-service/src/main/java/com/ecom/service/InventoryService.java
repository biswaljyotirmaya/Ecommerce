package com.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.entity.Inventory;
import com.ecom.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repo;

	public void reduceStock(Long productId, int qty) {

		Inventory inv = repo.findById(productId).orElseGet(() -> {
			Inventory i = new Inventory();
			i.setProductId(productId);
			i.setQuantity(0);
			return repo.save(i);
		});

		if (inv.getQuantity() < qty) {
			throw new RuntimeException("Insufficient stock");
		}

		inv.setQuantity(inv.getQuantity() - qty);
		repo.save(inv);
	}

	public void addStock(Long productId, int qty) {
		Inventory inv = repo.findById(productId).orElse(new Inventory());
		inv.setProductId(productId);
		inv.setQuantity(inv.getQuantity() + qty);
		repo.save(inv);
	}
}
