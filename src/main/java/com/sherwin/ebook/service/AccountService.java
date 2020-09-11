package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.domain.account.Account;
import com.sherwin.ebook.repository.AccountRepository;
import com.sherwin.ebook.repository.CartRepository;
import com.sherwin.ebook.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void addBilling(Account account, Billing billing) {

        if (account.getBilling() == null) {
            account.setBilling(billing);
        } else {
            account.getBilling().setFirstName(billing.getFirstName());
            account.getBilling().setLastName(billing.getLastName());
            account.getBilling().setEmail(billing.getEmail());
            account.getBilling().setAddress(billing.getAddress());
            account.getBilling().setCountry(billing.getCountry());
            account.getBilling().setState(billing.getState());
            account.getBilling().setZip(billing.getZip());

            account.getBilling().getPayment().setCreditCardNumber(billing.getPayment().getCreditCardNumber());
            account.getBilling().getPayment().setFullName(billing.getPayment().getFullName());
            account.getBilling().getPayment().setExpiration(billing.getPayment().getExpiration());
            account.getBilling().getPayment().setPaymentType(billing.getPayment().getPaymentType());
            account.getBilling().getPayment().setCVV(billing.getPayment().getCVV());
        }
        accountRepository.save(account);
    }
}
