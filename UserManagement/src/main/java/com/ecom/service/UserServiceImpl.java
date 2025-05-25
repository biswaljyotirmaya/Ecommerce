package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.config.AppConfig;
import com.ecom.cons.UserManagementConstant;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    
    private Map<String,String> message;

    @Autowired
    public UserServiceImpl(AppConfig config) {
    	message=config.getMsg();
    }
    @Override
    public String registerUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        User saved = userRepository.save(user);
        return user.getId()!=null?message.get(UserManagementConstant.SAVE_SUCCESS)+user.getId():message.get(UserManagementConstant.SAVE_FAILURE);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(message.get(UserManagementConstant.SAVE_FAILURE)+ userId));
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
                .orElseThrow(() -> new RuntimeException(message.get(UserManagementConstant.SAVE_FAILURE)+ userId));
       BeanUtils.copyProperties(userDto, user);
        User updated = userRepository.save(user);
        return userDto;
    }

    @Override
    public String deleteUser(Long userId) {
    	return message.get(UserManagementConstant.DELETE_SUCCESS)+userId;
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
