package com.mk.order.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mk.order.entities.Order;
import com.mk.order.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final String HOST_NAME = "HOSTNAME";

	private static final String DEFAULT_ENV_INSTANCE_GUID = "UNKNOWN";

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	private static List<Order> orders = new ArrayList<Order>();
	private static int orderCount = 3;

	static {
		orders.add(new Order(1, new BigDecimal("250"), new Date(), 1, "default"));
		orders.add(new Order(2, new BigDecimal("350"), new Date(), 1, "default"));
		orders.add(new Order(3, new BigDecimal("450"), new Date(), 2, "default"));
	}

	// @Value(${ENVIRONMENT_VARIABLE_NAME:DEFAULT_VALUE})
	@Value("${" + HOST_NAME + ":" + DEFAULT_ENV_INSTANCE_GUID + "}")
	private String hostName;

	@Override
	public List<Order> findAll() {
		return orders;
	}

	@Override
	public Order save(Order order) {
		LOGGER.info("Received Request to save HOST_NAME {}. ", hostName);
		order.setHost(hostName);
		order.setId(++orderCount);
		orders.add(order);
		return order;

	}

	@Override
	public List<Order> findByUserId(Integer userId) {
		List<Order> filteredOrders = new ArrayList<Order>();
		for (Order order : orders) {
			if (order.getUserId() == userId) {
				filteredOrders.add(order);
			}
		}
		return filteredOrders;
	}

	@Override
	public void deleteById(Integer id) {
		Iterator<Order> iterator = orders.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			if (order.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public Order findById(Integer id) {
		for (Order order : orders) {
			if (order.getId() == id) {
				return order;
			}
		}
		return null;
	}

}