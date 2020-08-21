package com.sherwin.ebook.controller;

import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public String home(Model model){
        List<Book> books = bookService.findAllSortedById();
        model.addAttribute("books",books);
        return "book/list";
    }

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable Long id,Model model){
        Book book = bookService.get(id);
        model.addAttribute("book",book);
        return "book/view";
    }

    @PostMapping("/book/search")
    public String search(@RequestParam("searchcontent") String searchcontent, Model model){
        List<Book> search = bookService.search(searchcontent);
        model.addAttribute("books",search);
        return "book/list";
    }

    @GetMapping("/book/ajaxsearch2")
    @ResponseBody
    public String showBook(Model model){
        return "3";
    }

    @GetMapping("/book/ajaxsearch3/{searchcontent}")
    public String showBook2(@PathVariable String searchcontent, Model model){
        List<Book> search = bookService.search(searchcontent);
        model.addAttribute("books",search);
        return "book/list :: table-refresh";
    }

}
