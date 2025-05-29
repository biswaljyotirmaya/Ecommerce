package com.ecom.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Category")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {


	@Id
	@SequenceGenerator(name="Gen4",sequenceName = "Seq_category",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "Gen4",strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 30)
	private String name;
	@Column(length = 200)
	private String description;
	
	@OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id",referencedColumnName = "id")
	private List<Product> products;

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
