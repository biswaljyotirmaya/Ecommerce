package com.ecom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.UserDto;
import com.ecom.entity.Address;
import com.ecom.entity.User;
import com.ecom.repository.IUserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public String createUser(UserDto userDto) {
        User user = dtoToEntity(userDto);
        user = userRepository.save(user);
        return "User registerd successfully with UserId: "+user.getId();
    }

    @Override
    public String updateUser(Long id, UserDto userDto) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        existUser.setName(userDto.getName());
        existUser.setEmail(userDto.getEmail());
        existUser.setPassword(userDto.getPassword());
        existUser.setRole(userDto.getRole());

        Long Id = userRepository.save(existUser);
        return "User updated successfully with UserId: "+Id;
    }

    @Override
    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        return "User deleted successfully with UserId: "+id;
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::entityToDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll();
    }

    private User dtoToEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        List<Address> addresses = dto.getAddress().stream().map(addr -> {
            addr.setUser(user);
            return addr;
        }).collect(Collectors.toList());

        user.setAddress(addresses);
        return user;
    }

    private UserDto entityToDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setAddress(user.getAddress());
        return dto;
    }
}
