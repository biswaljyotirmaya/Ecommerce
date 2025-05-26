package com.ecom.Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.UserDto;
import com.ecom.service.IUserService;

@RestController
@RequestMapping("/UserManagemnt-api")
class UserOperationsController {

	@Autowired
<<<<<<< HEAD
	private IUserService userService;

	// public String registerUser(UserDto userDto)

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {

=======
	private IUserService UserService;

	// public String registerUser(UserDto userDto)

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(UserDto userDto) {
>>>>>>> branch 'master' of https://github.com/biswaljyotirmaya/Ecommerce-
		try {

<<<<<<< HEAD
			String msg = userService.registerUser(userDto);
=======
			String msg = UserService.registerUser(userDto);
>>>>>>> branch 'master' of https://github.com/biswaljyotirmaya/Ecommerce-

			return new ResponseEntity<String>(msg, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Problem Occurs", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/allUser")
	public ResponseEntity<?> fetchAllUser() {
<<<<<<< HEAD
=======
		try {
			List<UserDto> list = UserService.getAllUsers();
>>>>>>> branch 'master' of https://github.com/biswaljyotirmaya/Ecommerce-

<<<<<<< HEAD
		try {
			List<UserDto> list = userService.getAllUsers();

			return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<String>("Got Internal Error::: Not Getting to fetch Users Details",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//    // UserDto updateUser(Long userId, UserDto userDto);

	@PutMapping("/modify/{id}")
	public ResponseEntity<String> modifyUser(@PathVariable("id") Long id, UserDto uDto) {

		try {

			userService.updateUser(id, uDto);
			return new ResponseEntity<String>("User Detalils Updated", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Problem Occur for Updation", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// String deleteUser(Long userId);

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") long id) {
		try {
			String resMsg = userService.deleteUser(id);
			return new ResponseEntity<String>(id + "User Deleted Sucessfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Problem Occur for Deletion", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// List<UserDto> getUsersByRole(String role);

	@GetMapping("/show-ByRole/{role}")
	public ResponseEntity<?> showUserByRole(@PathVariable("name") String role) {
		try {

			List<UserDto> list = userService.getUsersByRole(role);
=======
			return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Got Internal Error::: Not Getting to fetch Users Details",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//    // UserDto updateUser(Long userId, UserDto userDto);

	@PutMapping("/modify/{id}")
	public ResponseEntity<String> modifyUser(@PathVariable("id") Long id, UserDto uDto) {

		try {

			UserService.updateUser(id, uDto);
			return new ResponseEntity<String>("User Detalils Updated", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Problem Occur for Updation", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// String deleteUser(Long userId);

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") long id) {
		try {
			String resMsg = UserService.deleteUser(id);
			return new ResponseEntity<String>(id + "User Deleted Sucessfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Problem Occur for Deletion", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// List<UserDto> getUsersByRole(String role);

	@GetMapping("/show-ByRole/{role}")
	public ResponseEntity<?> showUserByRole(@PathVariable("name") String role) {
		try {

			List<UserDto> list = UserService.getUsersByRole(role);
>>>>>>> branch 'master' of https://github.com/biswaljyotirmaya/Ecommerce-
			return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Internal Problem ", HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
