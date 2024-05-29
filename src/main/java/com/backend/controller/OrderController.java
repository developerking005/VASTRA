package com.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.controller.model.Address;
import com.backend.controller.model.Order;
import com.backend.controller.model.User;
import com.backend.exception.OrderException;
import com.backend.exception.UserException;
import com.backend.service.OrderService;
import com.backend.service.UserService;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private OrderService orderService;
	
	private UserService userService;
	
	public OrderController(OrderService orderService,UserService userService) {
		this.orderService=orderService;
		this.userService=userService;
	
	}
		
	@PostMapping("/")
	public ResponseEntity<?> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization")String jwt) throws UserException{

		System.out.println("TEst - 2");
		User user = userService.findUserProfileByJwt(jwt);
		
		Order order = orderService.createOrder(user, shippingAddress);
		
		System.out.println("order"+order);
		System.out.println("Executed");
		return new ResponseEntity<>(order, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException, OrderException{
		User user = userService.findUserProfileByJwt(jwt);		
		
		List<Order> orders= orderService.userOrderHistory(user.getUserId());
		return new ResponseEntity<>(orders, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException{
		User user= userService.findUserProfileByJwt(jwt);
		
		Order order = orderService.findOrderById(orderId);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}
	
	

}
