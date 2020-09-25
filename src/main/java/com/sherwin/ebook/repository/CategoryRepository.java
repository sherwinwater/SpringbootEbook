package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Account;
import com.sherwin.ebook.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
