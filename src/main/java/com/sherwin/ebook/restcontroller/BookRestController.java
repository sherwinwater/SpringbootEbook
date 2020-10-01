package com.sherwin.ebook.restcontroller;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks(){
//        bookService.findAll().forEach(e-> System.out.println(e));
        return bookService.findAll();
    }

    @GetMapping("/book/{id}")
    public Book viewBook(@PathVariable Long id) {
        return bookService.get(id);
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
