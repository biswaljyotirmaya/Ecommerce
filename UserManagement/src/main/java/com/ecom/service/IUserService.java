package com.ecom.service;

import java.util.List;

import com.ecom.dto.UserDto;

public interface IUserService {
	String createUser(UserDto userDto);
	String updateUser(Long id, UserDto userDto);
	String deleteUser(Long id);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    List<UserDto> getAllUsers();
}
