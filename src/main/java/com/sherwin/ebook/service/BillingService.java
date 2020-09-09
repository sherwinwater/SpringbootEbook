package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.repository.BillingRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    public BillingService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
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
        billing.setOrder(order);

        billingRepository.save(billing);
    }
}
