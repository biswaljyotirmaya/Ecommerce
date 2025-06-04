package com.ecom.service;

import com.ecom.dto.UserDto;
import java.util.List;

public interface IUserService {

	public String registerUser(UserDto userDto);

	public UserDto getUserById(Long userId);

	public List<UserDto> getAllUsers();

	public UserDto updateUser(Long userId, UserDto userDto);

	public String deleteUser(Long userId);

	public List<UserDto> getUsersByRole(String role);

	public UserDto findUserByName(String name);
}
