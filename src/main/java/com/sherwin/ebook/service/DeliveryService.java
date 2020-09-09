package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Billing;
import com.sherwin.ebook.domain.Delivery;
import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
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
        delivery.setOrder(order);

        deliveryRepository.save(delivery);
    }
}
