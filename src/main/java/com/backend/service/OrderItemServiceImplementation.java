package com.backend.service;

import org.springframework.stereotype.Service;

import com.backend.controller.model.OrderItem;
import com.backend.repo.OrderItemRepository;


@Service
public class OrderItemServiceImplementation implements OrderItemService{

	private OrderItemRepository orderItemRepository;
	
	public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
		this.orderItemRepository=orderItemRepository;	
	}
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}

}
