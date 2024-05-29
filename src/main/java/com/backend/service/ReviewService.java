package com.backend.service;

import java.util.List;

import com.backend.controller.model.Review;
import com.backend.controller.model.User;
import com.backend.exception.ProductException;
import com.backend.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user)throws ProductException;
	
	public List<Review> getAllReview(Long productId);
}
