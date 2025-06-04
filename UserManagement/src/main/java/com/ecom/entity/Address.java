package com.ecom.entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
//github.com/biswaljyotirmaya/Ecommerce-
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
@Table(name="Address")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Address implements Serializable{

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
	@ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	@JsonIgnore
	private User user;

	@Override
	public String toString(){
		return "Address [id=" + id + ", city=" + city + ", state=" + state + ", country=" + country + "]";
	}
	


	
}
