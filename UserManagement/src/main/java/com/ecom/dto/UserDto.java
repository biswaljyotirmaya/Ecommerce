package com.ecom.dto;

import java.util.List;


import com.ecom.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private String name;
	private String email;
	private String password;
	private String role;
	private List<Address> address;
}
