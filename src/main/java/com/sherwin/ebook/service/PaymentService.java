package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Billing;
import com.sherwin.ebook.domain.Payment;
import com.sherwin.ebook.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

}
