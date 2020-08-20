package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);
    List<Book> findByNameContaining(String name);
    List<Book> findAllByOrderByIdAsc();
}
