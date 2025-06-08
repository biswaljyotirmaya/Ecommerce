package com.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductMgmtServiceImpl implements IProductMgmtService {

	@Autowired
	private IProductReository productRepo;
	@Autowired
	private IBrandRepository brandRepo;
	@Autowired
	private ICategoryRepository categoryRepo;
	private Map<String, String> msg;

	@Autowired
	private ProductMgmtServiceImpl(AppConfig config) {
		msg = config.getMessage();
	}

	@Override
	public String saveProduct(ProductDto dto) {
		log.info("Beafore Object creation");
		Product productEntity = new Product();
		Brand brandEntity = new Brand();
		Category categoryEntity = new Category();
		log.info("After Object creation");
		BrandDto brandDto = dto.getBrand();
		CategoryDto categoryDto = dto.getCategory();
		BeanUtils.copyProperties(dto, productEntity);
		BeanUtils.copyProperties(categoryDto, categoryEntity);
		BeanUtils.copyProperties(brandDto, brandDto);
		categoryRepo.save(categoryEntity);
		log.info("Before save method call");
		brandRepo.save(brandEntity);
		productEntity = productRepo.save(productEntity);
		log.info("After save method call");
		return productEntity.getId() != null ? msg.get(ProductConstant.SAVE_SUCCESS) + productEntity.getId()
				: msg.get(ProductConstant.SAVE_FAILURE);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<ProductDto> dto = new ArrayList<>();
		log.info("Beafore findAll() method");
		List<Product> list = productRepo.findAll();
		list.forEach((li) -> {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(li, productDto);
			dto.add(productDto);

		});

		return dto;
	}

	@Override
	public ProductDto findProductById(Long id) {
		ProductDto dto = new ProductDto();
		Optional<Product> opt = productRepo.findById(id);
		if (opt.isPresent()) {
			Product product = opt.get();
			BeanUtils.copyProperties(product, dto);

		}
		return dto;
	}
	
	@Override
	public List<ProductDto> findByNameAndCategoryaAndBrand(String name, Category category, Brand brand) {
		
	log.info("Before Product object creation");
	Product product=new Product();
	product.setBrand(brand);
	product.setName(name);
	product.setCategory(category);
	Example<Product> example=Example.of(product);
	log.info("Beafore findAll() method call");
	List<Product> list=productRepo.findAll(example);
	List<ProductDto> dtoList=new ArrayList<>();
	if(list.size()<=0|| list==null) {
		log.warn("list is null");
		return null;
	}
	
		BeanUtils.copyProperties(list, dtoList);
		return dtoList;
	}
	
	

}
