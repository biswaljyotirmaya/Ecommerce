package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ecom.config.AppConfig;
import com.ecom.cons.UserManagementConstant;
import com.ecom.dto.ActiveUser;
import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.repository.IUserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	private Map<String, String> message;

	@Autowired
	public UserServiceImpl(AppConfig config) {
		log.warn("inside config");
		message = config.getMessage();
		log.warn("initilazition of message");
	}

	@Override
	@CachePut(value = "Userdto", key = "#userdto.name")
	public String registerUser(UserDto userDto) {
		log.debug("before object creation");
		User user = new User();
		log.debug("after user object creation");

		BeanUtils.copyProperties(userDto, user);
		

		User saved = userRepository.save(user);
		log.info("after save ,ethod call");
		return saved.getId() != null ? message.get(UserManagementConstant.SAVE_SUCCESS) + saved.getId()
				: message.get(UserManagementConstant.SAVE_FAILURE);
	}

	@Override
	@Cacheable(value = "Userdto", key = "#userId")
	public UserDto getUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException(message.get(UserManagementConstant.SAVE_FAILURE) + userId));
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	@Override
	@Cacheable(value = "Userdtos")
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> list = new ArrayList<>();
		for (User user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			list.add(userDto);

		}
		return list;
	}
    @Override
    public UserDto findUserByName(String name) {
    	User user = userRepository.findUserByName(name);
    	UserDto dto=new UserDto();
    	BeanUtils.copyProperties(user, dto);
    	return dto;
    }
    
    @Override
    public UserDto activeUserByEmailOrName(ActiveUser activeUser) {
    	
    	User user=new User();
    	UserDto dto=new UserDto();
    	user.setEmail(activeUser.getEmail());
    	user.setName(activeUser.getName());
    	user.setPassword(activeUser.getConfirmpassword());
    	System.out.println(activeUser);
    	Example<User> example=Example.of(user);
    	List<User> list = userRepository.findAll(example);
    	if(list==null||list.size()<=0){
        	return dto;

    	}
    		User user2 = list.get(0);
    		if(user2.getEmail().equals(activeUser.getEmail())&& user2.getPassword().equals(activeUser.getConfirmpassword())) {
    			BeanUtils.copyProperties(user2,dto);
    			return dto;
    		
    		
    	}else if (user2.getName().equals(activeUser.getName())&& user2.getPassword().equals(activeUser.getConfirmpassword())) {
    		BeanUtils.copyProperties(user2,dto);
			return dto;
    		
    		
    	}
    
    	return dto;
    }

		

		
	

	@Override
	@CachePut(value = "Userdto", key = "#userDto.getId()")
	public UserDto updateUser(UserDto userDto) {
		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new RuntimeException(message.get(UserManagementConstant.SAVE_FAILURE) +userDto.getId()));
		BeanUtils.copyProperties(userDto, user);
		User updated = userRepository.save(user);
		return userDto;
	}

	@Override
	@CacheEvict(value = "Userdto", key = "#userId")
	public String deleteUser(Long userId) {
		Optional<User> opt = userRepository.findById(userId);
		userRepository.deleteById(userId);
		return opt.get().getId() != null ? message.get(UserManagementConstant.DELETE_SUCCESS)
				: "User Not found for deletion";
	}

	@Override
	@Cacheable(value = "Userdtos")
	public List<UserDto> getUsersByRole(String role) {
		List<User> users = userRepository.findByRole(role);
		List<UserDto> list = new ArrayList<>();
		for (User user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			list.add(userDto);
		}
		return list;
	}

	

	
}
