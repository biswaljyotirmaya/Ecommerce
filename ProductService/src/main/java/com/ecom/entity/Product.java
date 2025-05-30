package com.ecom.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product implements Serializable{

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
	
	@ManyToOne(targetEntity = Category.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="category_id",referencedColumnName = "id")
	private Category category;
	
	

	@ManyToOne(targetEntity = Brand.class,cascade = CascadeType.ALL)
	@JoinColumn(name="brand_id",referencedColumnName = "id")
	private Brand brand;
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", stock_Quantity=" + stock_Quantity + ", creationTime=" + creationTime + ", updationTime="
				+ updationTime + "]";
	}
	
}
