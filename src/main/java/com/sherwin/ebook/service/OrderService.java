package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Delivery;
import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.repository.DeliveryRepository;
import com.sherwin.ebook.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

}
