package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ecom.config.AppConfig;
import com.ecom.cons.ProductConstant;
import com.ecom.dto.BrandDto;
import com.ecom.dto.CategoryDto;
import com.ecom.dto.ProductDto;
import com.ecom.entity.Brand;
import com.ecom.entity.Category;
import com.ecom.entity.Product;
import com.ecom.repository.IBrandRepository;
import com.ecom.repository.ICategoryRepository;
import com.ecom.repository.IProductReository;

@Service
public class ProductMgmtServiceImpl implements IProductMgmtService {
	
	@Autowired
	private IProductReository productRepo;
	@Autowired
	private IBrandRepository brandRepo;
	@Autowired
	private ICategoryRepository categoryRepo;
	private Map<String,String> msg;
	
	@Autowired
	private ProductMgmtServiceImpl(AppConfig config){
		msg=config.getMessage();
	}

	@Override
	public String saveProduct(ProductDto dto){
		Product productEntity=new Product();
		Brand  brandEntity=new Brand();
		Category categoryEntity=new Category();
		BrandDto  brandDto=dto.getBrand();
		CategoryDto categoryDto=dto.getCategory();
		BeanUtils.copyProperties(dto, productEntity);
		BeanUtils.copyProperties(categoryDto,categoryEntity);
		BeanUtils.copyProperties(brandDto, brandDto);
		categoryRepo.save(categoryEntity);
		brandRepo.save(brandEntity);
		productEntity=productRepo.save(productEntity);
		return productEntity.getId()!=null?msg.get(ProductConstant.SAVE_SUCCESS)+productEntity.getId():msg.get(ProductConstant.SAVE_FAILURE);
	}
	@Override
	public List<ProductDto> getAllProducts() {
		List<ProductDto> dto=new ArrayList<>();
		List<Product> list = productRepo.findAll();
		list.forEach((li)->{
		ProductDto productDto=new ProductDto();	
		BeanUtils.copyProperties(li, productDto);
		dto.add(productDto);
		
		
		});
		
	
		return dto;
	}
	@Override
	
	public ProductDto findProductById(Long id) {
		ProductDto dto =new ProductDto();
			Optional<Product> opt = productRepo.findById(id);
			if(opt.isPresent()) {
				Product product = opt.get();
				BeanUtils.copyProperties(product, dto);
				
			}
		return dto;
	}
	

}
