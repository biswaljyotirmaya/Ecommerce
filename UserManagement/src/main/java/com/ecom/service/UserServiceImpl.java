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
		log.trace("inside UserServiceImpl");
		message = config.getMessage();
		log.info("initilazition of message");
	}

	@Override
	@CachePut(value = "Userdto", key = "#userdto.name")
	public String registerUser(UserDto userDto) {
		log.trace("inside registerUser");
		log.info("before object creation");
		User user = new User();
		log.debug("after user object creation");

		BeanUtils.copyProperties(userDto, user);

		log.trace("Saving the user");
		User saved = userRepository.save(user);
		if (saved.getId() != null) {
			log.debug("User registered successfully");
			return message.get(UserManagementConstant.SAVE_SUCCESS) + saved.getId();
		} else {
			log.warn("User failed to register");
			return message.get(UserManagementConstant.SAVE_FAILURE);
		}
	}

	@Override
	@Cacheable(value = "Userdto", key = "#userId")
	public UserDto getUserById(Long userId) {
		log.trace("inside getUserById");
		log.info("Finding the availablity of the the user");
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException(message.get(UserManagementConstant.SAVE_FAILURE) + userId));
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(user, dto);
		log.info("User found and returned");
		return dto;
	}

	@Override
	@Cacheable(value = "Userdtos")
	public List<UserDto> getAllUsers() {
		log.info("Inside getAlluser");
		log.info("Finding all available users");
		List<User> users = userRepository.findAll();
		List<UserDto> list = new ArrayList<>();
		for (User user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			list.add(userDto);
		}
		log.info("Users founded, converted and returned");
		return list;
	}

	@Override
	public UserDto findUserByName(String name) {
		log.info("Finding user by UserName");
		User user = userRepository.findUserByName(name);
		if (user == null) {
			log.warn("User not found with specified name");
		}
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(user, dto);
		log.info("Users founded, converted and returned");
		return dto;
	}

	@Override
	public UserDto activeUserByEmailOrName(ActiveUser activeUser) {
		User user = new User();
		UserDto dto = new UserDto();
		user.setEmail(activeUser.getEmail());
		user.setName(activeUser.getName());
		user.setPassword(activeUser.getConfirmpassword());
		log.info("Login credential collected from user");
		Example<User> example = Example.of(user);
		List<User> list = userRepository.findAll(example);
		log.info("Finding user as per the credential");
		if (list == null || list.size() <= 0) {
			log.warn("No user found!", list);
			return dto;
		}
		User user2 = list.get(0);
		log.info("verifying credential");
		if (user2.getEmail().equals(activeUser.getEmail())
				&& user2.getPassword().equals(activeUser.getConfirmpassword())) {
			log.debug("login credential verified for email and password");
			BeanUtils.copyProperties(user2, dto);
			return dto;

		} else if (user2.getName().equals(activeUser.getName())
				&& user2.getPassword().equals(activeUser.getConfirmpassword())) {
			log.debug("login credential verified for userName and password");
			BeanUtils.copyProperties(user2, dto);
			return dto;

		}
		log.info("User verified and returned");
		return dto;
	}

	@Override
	@CachePut(value = "Userdto", key = "#userDto.getId()")
	public UserDto updateUser(UserDto userDto) {
		log.trace("Finding user by userId");
		Optional<User> user = userRepository.findById(userDto.getId());
		if (user != null) {
			log.debug("User found with specific userId");
			BeanUtils.copyProperties(userDto, user.get());
			User updated = userRepository.save(user.get());
			log.debug("Updating new user data");
			BeanUtils.copyProperties(updated, userDto);
		} else {
			log.warn("User not found with provided used details");
			throw new RuntimeException(message.get(UserManagementConstant.SAVE_FAILURE) + userDto.getId());
		}
		log.trace("User data updated");
		return userDto;
	}

	@Override
	@CacheEvict(value = "Userdto", key = "#userId")
	public String deleteUser(Long userId) {
		log.trace("Inside deleteUser");
		log.info("Finding user by id for deleation");
		Optional<User> opt = userRepository.findById(userId);
		if (opt.isPresent()) {
			log.debug("User found");
			userRepository.deleteById(userId);
			log.info("user deleted");
			return message.get(UserManagementConstant.DELETE_SUCCESS);
		}
		return "User Not found for deletion";
	}

	@Override
	@Cacheable(value = "Userdtos")
	public List<UserDto> getUsersByRole(String role) {
		log.trace("Inside getUsersByRole");
		log.info("Finding user by role");
		List<User> users = userRepository.findByRole(role);
		List<UserDto> list = new ArrayList<>();
		for (User user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			list.add(userDto);
		}
		log.info("Returing users");
		return list;
	}

}
