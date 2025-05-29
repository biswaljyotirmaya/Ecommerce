package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.Product;

public interface IProductReository extends JpaRepository<Product, Long> {

}
