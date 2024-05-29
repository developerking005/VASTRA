package com.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.controller.model.Address;
import com.backend.controller.model.Cart;
import com.backend.controller.model.CartItem;
import com.backend.controller.model.Order;
import com.backend.controller.model.OrderItem;
import com.backend.controller.model.User;
import com.backend.exception.OrderException;
import com.backend.repo.AddressRepository;
import com.backend.repo.OrderItemRepository;
import com.backend.repo.OrderRepository;
import com.backend.repo.UserRepository;



@Service
public class OrderServiceImplementation implements OrderService{

	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
//	private OrderRepository orderRepository;
//	
//	
//	private CartService cartService;
//	
//	
//	private UserRepository userRepository;
//	
//	
//	private AddressRepository addressRepository;
//	
////	
//	private OrderItemService orderItemService;
//	
//	
//	private OrderItemRepository orderItemRepository;
	
	
//	public OrderServiceImplementation(OrderItemRepository orderItemRepository,
//			OrderItemService orderItemService,
//			AddressRepository addressRepository,
//			UserRepository userRepository,
//			CartService cartService,
//			OrderRepository orderRepository
//			) {
//		this.orderItemRepository=orderItemRepository;
//		this.addressRepository=addressRepository;
//		this.userRepository=userRepository;
//		this.cartService=cartService;
//		this.orderRepository=orderRepository;
//		this.orderItemService=orderItemService;		
//	
//	}
//	
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		shippingAddress.setUser(user);
		Address address= addressRepository.save(shippingAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart= cartService.findUserCart(user.getUserId());
		List<OrderItem> orderItems= new ArrayList<>();
		
		for(CartItem item:cart.getCartItem()) {
			OrderItem orderItem= new OrderItem();
			
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPirce(item.getDiscountedPrice());
			
			OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
			
		}
		
		Order createdOrder = new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		
		createdOrder.setDiscounte(cart.getDiscounte());
		createdOrder.setTotalItem(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.getPaymentDetails().setStatus("PENDING");
		createdOrder.setCreatedAt(LocalDateTime.now());
		
		Order savedOrder = orderRepository.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepository.save(item);
		}
		
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt= orderRepository.findById(orderId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("Order not exist with id : "+orderId);
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order= findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setStatus("COMPLETED");		
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		
		Order order= findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		
		return orderRepository.save(order);
		
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order = findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		
		return orderRepository.save(order);
	}

	@Override
	public List<Order> userOrderHistory(Long userID) throws OrderException {
		
		List<Order> orders= orderRepository.getUsersOrders(userID);
		
		return orders;
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order= findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
	
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		orderRepository.deleteById(orderId);
		
	}
	
	

}
