package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.domain.Account;
import com.sherwin.ebook.repository.AccountRepository;
import com.sherwin.ebook.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private BookRepository bookRepository;

    public AccountService(AccountRepository accountRepository, BookRepository bookRepository) {
        this.accountRepository = accountRepository;
        this.bookRepository = bookRepository;
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

    public void addDelivery(Account account, Delivery delivery) {

        if (account.getDelivery() == null) {
            account.setDelivery(delivery);
        } else {
            account.getDelivery().setFirstName(delivery.getFirstName());
            account.getDelivery().setLastName(delivery.getLastName());
            account.getDelivery().setEmail(delivery.getEmail());
            account.getDelivery().setAddress(delivery.getAddress());
            account.getDelivery().setCountry(delivery.getCountry());
            account.getDelivery().setState(delivery.getState());
            account.getDelivery().setZip(delivery.getZip());
        }
        accountRepository.save(account);
    }

    public void addFavorite(Account account, Long id) {
        Book book = bookRepository.findBookById(id);
        if (account.getFavorite() == null) {
            account.setFavorite(new Favorite());
        }
        if (account.getFavorite().getBooks() == null) {
            account.getFavorite().setBooks(new LinkedHashSet<>());
        }

        account.getFavorite().getBooks().add(book);
        account.getFavorite().setAccount(account);
//        book.setFavorites(account.getFavorite());
//        book.getFavorites().setAccount(account);
        accountRepository.save(account);
    }

    public void deleteFavorite(Account account, Favorite favorite,Long id) {

        Book bookSelected = favorite.getBooks().stream()
                .filter(book1 -> book1.getId() == id)
                .findAny()
                .orElse(null);

        favorite.removeFavorite(bookSelected);
        account.setFavorite(favorite);
        favorite.setAccount(account);
        accountRepository.save(account);
    }
}
