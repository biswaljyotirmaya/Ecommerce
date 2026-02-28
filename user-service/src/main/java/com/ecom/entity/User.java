package com.ecom.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String username;
	private String email;
	private String password;
	private String mobile;
	private boolean mobileVerified;

	@Enumerated(EnumType.STRING)
	private Role role;
}
