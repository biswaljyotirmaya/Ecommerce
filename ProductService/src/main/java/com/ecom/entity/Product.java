package com.ecom.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Product {

	@Id
	@SequenceGenerator(name = "Gen3",sequenceName = "Seq_Product",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "Gen3",strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 30)
	private String name;
	@Column(length = 200)
	private String description;
	private Long price;
	private Long stock_Quantity;
	@CreationTimestamp
	@Column(insertable = true,updatable = false)
	private LocalDateTime creationTime;
	@UpdateTimestamp
	@Column(updatable = true,insertable = false)
	private LocalDateTime updationTime;
	
}
