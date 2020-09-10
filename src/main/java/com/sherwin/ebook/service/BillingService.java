package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.repository.BillingRepository;
import com.sherwin.ebook.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BillingService {

    private  BillingRepository billingRepository;
    private OrderRepository orderRepository;

    public BillingService(BillingRepository billingRepository, OrderRepository orderRepository) {
        this.billingRepository = billingRepository;
        this.orderRepository = orderRepository;
    }

    public void save(Billing billing) {
        billingRepository.save(billing);
    }

    public void addBilling(Order order, Billing billing) {
        Long idBilling = 1L;
        Long idPayment = 1L;
        if (order.getBilling() != null) {
            idBilling = order.getBilling().getId();
            if (order.getBilling().getPayment() != null) {
                idPayment = order.getBilling().getPayment().getId();
            }
        }
        order.setBilling(billing);
        order.getBilling().setCreatedBy(order.getCreatedBy());
        order.getBilling().setCreationDate(order.getCreationDate());
        order.getBilling().setPayment(billing.getPayment());
        order.getBilling().getPayment().setId(idPayment);
        order.getBilling().setId(idBilling);
        order.getBilling().getPayment().setBilling(billing);
        billing.setPayment(order.getBilling().getPayment());
//        billing.setOrder(order);

        orderRepository.save(order);  // order maintain the relationship between order and billing. not billing.
    }
}
