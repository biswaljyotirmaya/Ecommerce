package com.ecom.service;

import java.util.List;

import com.ecom.dto.ActiveUser;
import com.ecom.dto.UserDto;

public interface IUserService {

	public String registerUser(UserDto userDto);

	public UserDto getUserById(Long userId);

	public List<UserDto> getAllUsers();

	public UserDto updateUser(UserDto userDto);

	public String deleteUser(Long userId);

	public List<UserDto> getUsersByRole(String role);

	public UserDto findUserByName(String name);

	public UserDto activeUserByEmailOrName(ActiveUser activeUser);

}
