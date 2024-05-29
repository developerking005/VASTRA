package com.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.backend.controller.model.Product;
import com.backend.exception.ProductException;
import com.backend.request.CreateProductRequest;



public interface ProductService {
	
	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId, Product req) throws ProductException;
	
	public Product findProductById(Long id) throws ProductException;
	
	public List<Product> findProductByCategory(String category);
	
	public List<Product> findAllProducts();
	
	public Page<Product> getAllProduct(String category, List<String>colors, List<String>sizes, Integer minPrice, Integer maxPrice, 
			Integer minDisscount, String sort, String stock, Integer pageNumber, Integer pageSize);

}
