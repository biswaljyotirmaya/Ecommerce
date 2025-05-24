package com.ecom.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

public class Brand {

	@Id
	@SequenceGenerator(name = "Gen4",sequenceName = "Seq_Brand",initialValue = 1,allocationSize = 1)
	@GeneratedValue()
	private Long id;
	private String name;
}
