package com.ecom.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Address")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {

	@Id
	@SequenceGenerator(name = "Gen2",sequenceName = "Seq_Adddress",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "Gen2",strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 20)
	private String city;
	@Column(length = 20)
	private String state;
	@Column(length = 30)
	private String country;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") 
	private User user;

	
}
