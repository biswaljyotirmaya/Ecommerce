package com.ecom.dto;

import com.ecom.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {


	private String city;
	private String state;
	private String country;
	private User user;
}
