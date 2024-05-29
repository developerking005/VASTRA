package com.backend.service;

import java.util.List;

import com.backend.controller.model.Rating;
import com.backend.controller.model.User;
import com.backend.exception.ProductException;
import com.backend.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest re, User user)throws ProductException;
	
	public List<Rating> getProductRating(Long productId);
	
	
		

}
