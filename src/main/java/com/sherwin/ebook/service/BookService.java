package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(Book Book) {
        bookRepository.save(Book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public List<Book> findAllSortedById() {
        return bookRepository.findAllByOrderByIdAsc();
    }

    public Book get(Long id) {
        return bookRepository.findBookById(id);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> search(String content) {
        return bookRepository.findByNameContaining(content);
    }

}
