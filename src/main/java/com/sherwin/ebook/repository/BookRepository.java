package com.sherwin.ebook.repository;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookById(Long id);
    List<Book> findByNameContainingIgnoreCase(String name);
    List<Book> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String name,String location);
    List<Book> findByLocationContainingIgnoreCase(String location);
    List<Book> findByNameOrLocation(String name,String location);
    List<Book> findAllByOrderByIdAsc();
    Page<Book> findAll(Pageable pageable);
    Page<Book> findAllByCategory(Category category,Pageable pageable);
}
