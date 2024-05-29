package com.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.controller.model.Cart;
import com.backend.controller.model.CartItem;
import com.backend.controller.model.Product;
import com.backend.controller.model.User;
import com.backend.exception.ProductException;
import com.backend.repo.CartRepository;
import com.backend.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService {

	
	private CartRepository cartRepository;

	
	private CartItemService cartItemService;

	
	private ProductService productService;

	
	public CartServiceImplementation(CartRepository cartRepository,CartItemService cartItemService,ProductService productService) {
	  this.cartItemService=cartItemService; 
	  this.cartRepository=cartRepository;
	  this.productService=productService;
	  	  
}
	 

	@Override
	public Cart creatCart(User user) {
		Cart cart= new Cart();
		cart.setUser(user);
				
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart= cartRepository.findByUserId(userId);
		
		Product product= productService.findProductById(req.getProductId());
		
		CartItem isPresent = cartItemService.isCartItemExists(cart, product, req.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price =req.getQuantity()* product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItem().add(createdCartItem);
		}
		
		
		return "Item Add to Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		
		Cart cart = cartRepository.findByUserId(userId);
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItem()) {
			
			totalPrice= totalPrice+cartItem.getPrice();			
			totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem=totalItem +cartItem.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounte(totalPrice-totalDiscountedPrice);
		
		
		return cartRepository.save(cart);
	}

}
