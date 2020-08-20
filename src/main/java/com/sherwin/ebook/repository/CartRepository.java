package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart>  findCartByCustomer(Customer customer);
    Cart findCartById(Long id);

}
