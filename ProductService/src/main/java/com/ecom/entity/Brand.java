package com.ecom.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Brand")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Brand  implements Serializable{

	@Id
	@SequenceGenerator(name = "Gen4",sequenceName = "Seq_Brand",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "Gen4",strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 30)
	private String name;
	@OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id",referencedColumnName = "id")
	private List<Product> products;
	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + "]";
	}
}
