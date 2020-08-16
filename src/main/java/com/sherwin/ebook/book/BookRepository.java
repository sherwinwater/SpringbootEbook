package com.sherwin.ebook.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);
    List<Book> findByNameContaining(String name);
}
