package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Page<Book> getAllBooks(Integer pageNo, Integer pageSize,String sortBy){
        Pageable paging = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        return bookRepository.findAll(paging);

//        if(pagedResult.hasContent()){
//            return pagedResult;
//        }else {
//            return new Page<Book>();
//        }
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
