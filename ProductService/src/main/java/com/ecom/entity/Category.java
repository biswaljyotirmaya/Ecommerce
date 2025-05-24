package com.ecom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id
	@SequenceGenerator(name="Gen4",sequenceName = "Seq_category",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "Gen4",strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 30)
	private String name;
	@Column(length = 200)
	private String description;
}
