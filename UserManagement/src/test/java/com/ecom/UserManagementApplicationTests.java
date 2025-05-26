package com.ecom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.ecom.config.AppConfig;
import com.ecom.dto.UserDto;
import com.ecom.entity.Address;
import com.ecom.entity.User;
import com.ecom.repository.IUserRepository;
import com.ecom.service.UserServiceImpl;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserManagementApplicationTests {

	@Mock
	private IUserRepository userRepo;

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private AppConfig config;

	@Test
	public void registerUserTest(){
		
		UserDto dto = new UserDto();
		Address address = new Address();
		address.setCity("hyd");
		address.setCountry("India");
		address.setId(1L);
		address.setState("Telangana");
		
		dto.setEmail("abc@gmail.com");
		dto.setName("Omkar");
		dto.setPassword("safari");
		dto.setRole("admin");
		dto.setAddress(List.of(address));

		User user = new User();
		user.setId(1L);
		address.setUser(user);
		user.setAddress(List.of(address));
		
		BeanUtils.copyProperties(dto, user);

		Mockito.when(userRepo.save(any(User.class))).thenReturn(user);
		String msg=service.registerUser(dto);
		
		assertEquals("User is registered with id",msg);
	}
}
