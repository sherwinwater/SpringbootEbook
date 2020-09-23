package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Role;
import com.sherwin.ebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    List<Role> findAll();
}
