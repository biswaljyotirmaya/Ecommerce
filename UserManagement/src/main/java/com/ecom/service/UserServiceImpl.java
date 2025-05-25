package com.ecom.service;

import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public String registerUser(UserDto userDto) {
        User user = mapToEntity(userDto);
        User saved = userRepository.save(user);
        return "User registered successfully with id: "+saved.getId();
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setAddress(userDto.getAddress());

        User updated = userRepository.save(user);
        return mapToDto(updated);
    }

    @Override
    public String deleteUser(Long userId) {
    	return "User deleted successfully with id: "+userId);
    }

    @Override
    public List<UserDto> getUsersByRole(String role) {
        List<User> users = userRepository.findByRole(role);
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setAddress(user.getAddress());
        return dto;
    }

    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setAddress(dto.getAddress());
        return user;
    }
}
