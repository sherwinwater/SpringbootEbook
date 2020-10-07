package com.sherwin.ebook.restcontroller;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        try{
            Book book = bookService.get(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public void add(@RequestBody Book book) {
        bookService.save(book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) {
        try{
            Book existingBook = bookService.get(id);
            bookService.save(book);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/book/search/{ajxsearchcontent}")
    public List<Book> search(@PathVariable("ajxsearchcontent") String ajxsearchcontent){
        System.out.println(ajxsearchcontent);
        return  bookService.search(ajxsearchcontent);
    }

    @GetMapping("/ajaxsearch/book")
    public List<Book> showBook(Model model){
        List<Book> results = bookService.search("");
        model.addAttribute("Books",results);
//        return "Book/list :: table-refresh";
        return results;
    }
}
