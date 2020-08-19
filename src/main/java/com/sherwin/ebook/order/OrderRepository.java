package com.sherwin.ebook.order;

import com.sherwin.ebook.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order>  findCartByCustomer(Customer customer);
    Order findCartById(Long id);

}
