package com.ecom.Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.ActiveUser;
import com.ecom.dto.UserDto;
import com.ecom.service.IUserService;

@RestController
@RequestMapping("/UserManagement-api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserOperationsController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            String msg = userService.registerUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal error while registering a new user.");
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> fetchAllUsers() {
        try {
            List<UserDto> list = userService.getAllUsers();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user details.");
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyUser(@RequestBody UserDto uDto) {
        try {
            userService.updateUser(uDto);
            return ResponseEntity.ok("User details updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id) {
        try {
            String msg = userService.deleteUser(id);
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user with ID: " + id);
        }
    }

    @GetMapping("/show-ByRole/{role}")
    public ResponseEntity<?> showUserByRole(@PathVariable String role) {
        try {
            List<UserDto> list = userService.getUsersByRole(role);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching users by role.");
        }
    }

    @GetMapping(value = {"/login/{email}/{password}", "/login1/{name}/{password}"})
    public ResponseEntity<?> loginByEmailOrName(
            @PathVariable(required = false) String email,
            @PathVariable(required = false) String name,
            @PathVariable String password) {

        try {
            ActiveUser user = new ActiveUser();
            user.setEmail(email);
            user.setName(name);
            user.setConfirmpassword(password);

            UserDto dto = userService.activeUserByEmailOrName(user);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed. Please check credentials.");
        }
    }
}
