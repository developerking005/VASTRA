package com.backend.controller;

import java.util.List;

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

import com.backend.controller.model.ApiResponse;
import com.backend.controller.model.Product;
import com.backend.exception.ProductException;
import com.backend.request.CreateProductRequest;
import com.backend.service.ProductService;


@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
		
	private ProductService productService;
	
	public AdminProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
		
		Product product = productService.createProduct(req);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)throws ProductException{
		productService.deleteProduct(productId);
		ApiResponse res= new ApiResponse();
		res.setMessage("product deleted Successfully");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct(){
//		List<Product> products =productService.findAllProducts();
		List<Product> products =productService.findAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product req, @PathVariable Long productId)throws ProductException{
		Product product = productService.updateProduct(productId, req);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
	}
	
//	agar ek sath sara data bhejna ho array m to apan ye use kr skte h 
	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMutlitpleProduct(@RequestBody CreateProductRequest[] req){
		for(CreateProductRequest product:req) {
			productService.createProduct(product);			
		}
		ApiResponse res= new ApiResponse();
		res.setMessage("All Oroduct Sucessfully created");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
		
	}
	
	
	
	

}
