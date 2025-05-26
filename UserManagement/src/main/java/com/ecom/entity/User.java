package com.ecom.entity;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@SequenceGenerator(name = "Gen1",sequenceName ="Seq_User",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "Gen1",strategy =GenerationType.SEQUENCE)
	private Long id;
	@Column(length = 30)
	private String name;
	@Column(length = 50)
	private String email;
	@Column(length = 20)
	private String password;
	@Column(length = 20)
	private String role;
	@CreationTimestamp
	@Column(insertable = true,updatable = false)
	private LocalDateTime creationTime;
	@UpdateTimestamp
	@Column(updatable = true,insertable = false)
	private LocalDateTime updationTime;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Address> address;

}
