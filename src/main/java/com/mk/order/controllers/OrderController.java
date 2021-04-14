package com.mk.order.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mk.order.entities.Order;
import com.mk.order.services.OrderService;

@RestController
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public String imHealthy() {
		return "{healthy:true}";
	}

	@GetMapping("/{id}")
	public Order getByOrderId(@PathVariable int id) {
		LOGGER.info("Received Request to getByOrderId {}. ", id);
		return orderService.findById(id);
	}

	@GetMapping("/orders")
	public List<Order> list() {
		return orderService.findAll();
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<List<Order>> get(@PathVariable Integer id) {
		try {
			List<Order> orders = orderService.findByUserId(id);
			return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<List<Order>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/orders")
	public ResponseEntity<Order> add(@RequestBody Order order) {
		return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);

	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<?> update(@RequestBody Order order, @PathVariable Integer id) {
		try {
			orderService.save(order);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}