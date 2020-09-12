package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Delivery;
import com.sherwin.ebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
