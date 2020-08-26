package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Delivery;
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

}
