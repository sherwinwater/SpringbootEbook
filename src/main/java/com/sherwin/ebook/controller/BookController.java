package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.Category;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

//    @GetMapping("/book")
//    public String home(Model model) {
//        return "redirect";
//    }

    @GetMapping("/")
    public String getAllBooks(Model model,HttpSession session){
        Category category = categoryService.getCategory().get(0);
        session.setAttribute("category",category);
        return viewPage(model,1,3,"id",session);
    }

    @GetMapping("/book/category/{name}")
    public String getAllBooksByCategory(Model model, @PathVariable String name,HttpSession session){
        Category category = categoryService.getCategoryByName(name);
        session.setAttribute("category",category);
        return viewPage(model,1,3,"id",session);
    }

    @GetMapping("/page/{pageNum}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum,
                           @RequestParam(defaultValue = "3") Integer pageSize,
                           @RequestParam(defaultValue = "id") String sortBy,
                           HttpSession session) {

//        Page<Book> page = bookService.getAllBooks(pageNum-1,pageSize,sortBy);
        Category category = (Category) session.getAttribute("category");
        Page<Book> page = bookService.getAllBooksByCategory(pageNum-1,pageSize,sortBy,category);
        List<Book> books = page.getContent();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        List<Category> categories = categoryService.getCategory();

        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        session.setAttribute("pageNum",pageNum);

        return "book/home";
//        return "book/lists";
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
