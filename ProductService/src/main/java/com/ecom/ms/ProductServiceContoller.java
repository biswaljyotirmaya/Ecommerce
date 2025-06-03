package com.ecom.ms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.ecom.dto.ProductDto;
import com.ecom.service.IProductMgmtService;

@RestController
@RequestMapping("product-api")
public class ProductServiceContoller {
	
	@Autowired
	private IProductMgmtService service;
	 
	@PostMapping("/save")
	public ResponseEntity<String> registerProduct(@RequestBody ProductDto dto){
	
		try {
		String msg = service.saveProduct(dto);
		return new ResponseEntity<String>(msg,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Details are missing",HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductDto>> findAllProducts(){
		
			List<ProductDto> list=service.getAllProducts();
			return new ResponseEntity<List<ProductDto>>(list,HttpStatus.OK);
		
	}
}
