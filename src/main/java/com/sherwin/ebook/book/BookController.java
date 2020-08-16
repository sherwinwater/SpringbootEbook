package com.sherwin.ebook.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String home(Model model){
        List<Book> books = bookService.findAll();
        model.addAttribute("books",books);
        return "book/list";
    }

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable Long id,Model model){
        Book book = bookService.get(id);
        model.addAttribute("book",book);
        return "book/view";
    }

    @PostMapping("/search/book")
    public String search(@RequestParam("searchcontent") String searchcontent, Model model){
        List<Book> search = bookService.search(searchcontent);
        model.addAttribute("books",search);
        return "book/list";
    }

    @GetMapping("/ajaxsearch2/book")
    @ResponseBody
    public String showBook(Model model){
        return "3";
    }

    @GetMapping("/ajaxsearch3/book/{searchcontent}")
    public String showBook2(@PathVariable String searchcontent, Model model){
        List<Book> search = bookService.search(searchcontent);
        model.addAttribute("books",search);
        return "book/list :: table-refresh";
    }

}
