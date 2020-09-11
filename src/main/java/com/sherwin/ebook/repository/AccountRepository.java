package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
