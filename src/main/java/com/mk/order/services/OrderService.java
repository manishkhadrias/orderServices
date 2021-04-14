package com.mk.order.services;

import java.util.List;

import com.mk.order.entities.Order;

public interface OrderService {

	List<Order> findAll();

	Order save(Order order);

	Order findById(Integer id);

	void deleteById(Integer id);

	List<Order> findByUserId(Integer userId);

}