package com.ecom.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.ProductDto;
import com.ecom.service.IProductMgmtService;

@RestController
@RequestMapping("product-api")
public class ProductServiceContoller {
	
	@Autowired
	private IProductMgmtService service;
	
	public ResponseEntity<String> registerProduct(@RequestBody ProductDto dto){
	
		try {
		String msg = service.saveProduct(dto);
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Details are missing",HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
}
