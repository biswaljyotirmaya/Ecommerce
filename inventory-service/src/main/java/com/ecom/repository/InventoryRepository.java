package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
