package com.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.controller.model.Cart;
import com.backend.controller.model.CartItem;
import com.backend.controller.model.Product;
import com.backend.controller.model.User;
import com.backend.exception.CartItemException;
import com.backend.exception.UserException;
import com.backend.repo.CartItemRepository;



@Service
public class CartItemServiceImplmentation implements CartItemService{

	
	private CartItemRepository cartItemRepostory;
	
	private UserService userService;
		
	public CartItemServiceImplmentation(CartItemRepository cartItemRepostory,UserService userService) {
		
		this.cartItemRepostory=cartItemRepostory;
		this.userService=userService;
	
	}
	
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
		
		CartItem createdCartItem =cartItemRepostory.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item = findCartItemById(id);
		User user =userService.findUserById(item.getUserId());
		if(user.getUserId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getDiscountedPrice()*item.getQuantity());
		}
		
		return cartItemRepostory.save(item);
		
	}

	@Override
	public CartItem isCartItemExists(Cart cart, Product product, String size, Long userId) {
		
		CartItem cartItem = cartItemRepostory.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		
		CartItem cartItem=findCartItemById(cartItemId);
		
		User user = userService.findUserById(cartItem.getUserId());
		
//		Logged in user is reqUser
		User reqUser= userService.findUserById(userId);
		
		if(user.getUserId().equals(reqUser.getUserId())) {
			cartItemRepostory.deleteById(cartItemId);
		}
		else {
			throw new UserException("You can't remove another user items");			
		}		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		
		Optional<CartItem> opt=cartItemRepostory.findById(cartItemId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cart Item is not found with id :" + cartItemId);
		
	}

}
