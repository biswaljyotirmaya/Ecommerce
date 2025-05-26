package com.ecom.Rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.UserDto;
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

    @GetMapping("/allUser")
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
    
//    // UserDto updateUser(Long userId, UserDto userDto);
    
    @PutMapping("/modify/{id}")
   public ResponseEntity<String> modifyUser(@PathVariable("id") Long id,UserDto uDto)
    {
    	
    	try {
    	
    		UserService.updateUser(id, uDto);
    		return new ResponseEntity<String>("User Detalils Updated",HttpStatus.OK);
    		
    		
    	}
    	catch(Exception e)
    	{
    		return new ResponseEntity<String>("Problem Occur for Updation",HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
}
