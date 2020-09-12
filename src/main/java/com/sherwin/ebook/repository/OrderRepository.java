package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByUser(User user);
    Optional<Order> findFirstByUserOrderByIdDesc(User user);
    Order findOrderByStatusAndUser(String status,User user);

    Order findOrderById(Long id);
}
