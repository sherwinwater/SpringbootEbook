package com.sherwin.ebook.controller;

import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public String home(Model model) {
        List<Book> books = bookService.findAllSortedById();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model){
        return viewPage(model,1,8,"id");
    }

    @GetMapping("/page/{pageNum}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum,
                           @RequestParam(defaultValue = "8") Integer pageSize,
                           @RequestParam(defaultValue = "id") String sortBy) {

        Page<Book> page = bookService.getAllBooks(pageNum-1,pageSize,sortBy);
        List<Book> books = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("books", books);

        return "book/lists";
    }

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "book/view";
    }

    @PostMapping("/book/search")
    public String search(@RequestParam("searchcontent") String searchcontent, Model model) {
        List<Book> search = bookService.search(searchcontent);
        model.addAttribute("books", search);
        return "book/list";
    }

    @GetMapping("/book/ajaxsearch2")
    @ResponseBody
    public String showBook(Model model) {
        return "3";
    }

    @GetMapping("/book/ajaxsearch3/{searchcontent}")
    public String showBook2(@PathVariable String searchcontent, Model model) {
        List<Book> search = bookService.search(searchcontent);
        model.addAttribute("books", search);
        return "book/list :: table-refresh";
    }

}
