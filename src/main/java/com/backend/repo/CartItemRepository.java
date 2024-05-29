package com.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.controller.model.Cart;
import com.backend.controller.model.CartItem;
import com.backend.controller.model.Product;



public interface CartItemRepository  extends JpaRepository<CartItem, Long>{

	@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product")Product product, @Param("size")String size,			
			@Param("userId")Long userId);
	
	@Query("SELECT ci FROM CartItem ci WHERE ci.userId = :userId")
	public List<CartItem> findByCartId(@Param("userId") Long userId);
}
