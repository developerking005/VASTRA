package com.backend.service;

import com.backend.controller.model.Cart;
import com.backend.controller.model.User;
import com.backend.exception.ProductException;
import com.backend.request.AddItemRequest;

public interface CartService {
	
	public Cart creatCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);

}
