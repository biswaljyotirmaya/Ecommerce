package com.ecom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

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
	
	@Mock
	private UserServiceImpl service;

	@Test
	public void contextLoads() {
	
		UserDto dto =new UserDto();
		Address ads=new Address();
		User user=new User();
		List<Address> list=List.of(ads);
		ads.setCity("hyd");
		ads.setCountry("India");
		ads.setId(7845784L);
		ads.setState("Telangana");
		dto.setEmail("abc@gmail.com");
		dto.setName("Omkar");
		dto.setPassword("safari");
		dto.setRole("admin");
		dto.setAddress(list);
		user.setId(1l);
		
		BeanUtils.copyProperties(dto,user);
		
		
		Mockito.when(userRepo.save(user)).thenReturn(user);
		
		//get actual result
		
		String msg=service.registerUser(dto);
		
		assertEquals(null, null);
		
		
		
		
		
	
	}

}
