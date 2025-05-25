package com.ecom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public String registerUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        User saved = userRepository.save(user);
        return "User registered successfully with id: "+saved.getId();
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        UserDto dto=new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> list=new ArrayList<>();
        for(User user: users){
        	BeanUtils.copyProperties(users, list);
        	return list;
        }
       
       return list; 
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
       BeanUtils.copyProperties(userDto, user);
        User updated = userRepository.save(user);
        return userDto;
    }

    @Override
    public String deleteUser(Long userId) {
    	return "User deleted successfully with id: "+userId;
    }
    

    @Override
    public List<UserDto> getUsersByRole(String role) {
        List<User> users = userRepository.findByRole(role);
        List<UserDto> list=new ArrayList<>();
        for(User user: users) {
        	BeanUtils.copyProperties(users, list);
        	return list;
        }
        return list;
    }

   

  
}
