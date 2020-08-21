package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);
    List<User> findByFirstNameOrLastNameOrEmailContaining(String firstname,String lastname,String email);
}
