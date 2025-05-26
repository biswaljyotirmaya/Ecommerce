package com.ecom.Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.UserDto;
import com.ecom.entity.User;
import com.ecom.service.IUserService;


@RestController
@RequestMapping("/User")
class UserOperationsController {

	@Autowired
	private IUserService UserService;
	
	
    //public String registerUser(UserDto userDto)
    
    @PostMapping("/register")
	public ResponseEntity<String> registerUser(UserDto userDto)
	{
		try {
			
			String msg = UserService.registerUser(userDto);
			
			return new ResponseEntity<String>(msg,HttpStatus.CREATED);
			
		}catch(Exception e)
		{
			return new ResponseEntity<String>("Internal Problem Occurs",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

    
    public ResponseEntity<?> fetchAllUser()
    {
    	try
    	{
    		List<UserDto> list = UserService.getAllUsers();
    		
    		return new ResponseEntity<List<UserDto>>(list,HttpStatus.OK);
    		
    		
    	}
    	catch(Exception e)
    	{
    		return new ResponseEntity<String>("Got Internal Error::: Not Getting to fetch Users Details",
    				HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
}
