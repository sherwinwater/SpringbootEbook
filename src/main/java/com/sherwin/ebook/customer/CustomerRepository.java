package com.sherwin.ebook.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerById(Long id);
    List<Customer> findByFirstNameOrLastNameOrEmailIdContaining(String firstname,String lastname,String email);
}
