package com.ecom.service;

import com.ecom.dto.UserDto;
import java.util.List;

public interface IUserService {

    String registerUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto userDto);

    String deleteUser(Long userId);

    List<UserDto> getUsersByRole(String role);
}
