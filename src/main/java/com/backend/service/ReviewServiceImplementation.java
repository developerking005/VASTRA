package com.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.controller.model.Product;
import com.backend.controller.model.Review;
import com.backend.controller.model.User;
import com.backend.exception.ProductException;
import com.backend.repo.ProductRepository;
import com.backend.repo.ReviewRepository;
import com.backend.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService{

	private ReviewRepository reviewRepository;
	
	private ProductService productService;
	
	private ProductRepository productRepository;
		
	public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
		this.reviewRepository=reviewRepository;
		this.productService=productService;
		this.productRepository=productRepository;
	}
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {

		Product product = productService.findProductById(req.getProductId());
		
		Review review= new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());		
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
