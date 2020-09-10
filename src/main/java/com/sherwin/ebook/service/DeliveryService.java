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
        Long idDelivery = 1L;
        if (order.getDelivery() != null) {
            idDelivery = order.getDelivery().getId();
        }
        order.setDelivery(delivery);
        order.getDelivery().setCreationDate(order.getCreationDate());
        order.getDelivery().setCreatedBy(order.getCreatedBy());
        order.getDelivery().setId(idDelivery);
//        delivery.setOrder(order);

        orderRepository.save(order);  // order maintain the relationship between order and billing. not billing.
        deliveryRepository.save(delivery);
    }
}
