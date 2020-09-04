package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Billing;
import com.sherwin.ebook.repository.BillingRepository;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    public BillingService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    public void save(Billing billing) {
        billingRepository.save(billing);
    }

}
