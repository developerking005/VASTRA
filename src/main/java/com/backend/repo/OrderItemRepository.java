package com.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.controller.model.OrderItem;



public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	

}
