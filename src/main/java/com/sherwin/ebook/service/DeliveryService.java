package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Billing;
import com.sherwin.ebook.domain.Delivery;
import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.repository.DeliveryRepository;
import com.sherwin.ebook.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private DeliveryRepository deliveryRepository;
    private OrderRepository orderRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, OrderRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
    }

    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    public void addDelivery(Order order, Delivery delivery) {

        if (order.getDelivery() == null) {
            order.setDelivery(delivery);
        } else {
            order.getDelivery().setFirstName(delivery.getFirstName());
            order.getDelivery().setLastName(delivery.getLastName());
            order.getDelivery().setEmail(delivery.getEmail());
            order.getDelivery().setAddress(delivery.getAddress());
            order.getDelivery().setCountry(delivery.getCountry());
            order.getDelivery().setState(delivery.getState());
            order.getDelivery().setZip(delivery.getZip());
        }
        orderRepository.save(order);  // order maintain the relationship between order and billing. not billing.
    }
}
