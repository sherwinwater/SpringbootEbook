package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.Account;
import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @GetMapping("/admin")
    public String goAdminHome(){
        return "admin/home";
    }

    @GetMapping("/admin2")
    public String goAdminHome2(){
        return "admin/home2";
    }


//    -----user-----
    @RequestMapping("/admin/user/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/view";
    }

    @GetMapping("/admin/user/update/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/updateUser";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@Valid User user, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/user/updateUser";
        } else {
            userService.updateUser(user);
            return "redirect:/admin/user/view";
        }
    }


    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/admin/user/view";
    }

    @GetMapping("/admin/user/create")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/userform";
    }

    @PostMapping("/admin/user/save")
    public String saveUser(@Valid User user, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.findAll());
            return "user/list";
        } else {
            userService.save(user);
            return "redirect:/admin/user/"+user.getId();
        }
    }

    @GetMapping("/admin/user/view")
    public String home(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "admin/user/viewUsers";
    }

    @GetMapping("/admin/user/add")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/addUser";
    }

    @PostMapping("/admin/user/add")
    public String registerNewUser(@Valid User user, BindingResult bindingResult,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/user/addUser";
        } else {
            User newUser = userService.register(user);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success", true);
            return "redirect:/admin/user/add";
        }
    }


//------product------
    @GetMapping("/admin/product/add")
    public String addProductHome(Model model, Authentication authentication){
        Book book = new Book();
        model.addAttribute("book", book);
        return "admin/product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addBook(@Valid Book book, BindingResult bindingResult,
                          Model model, RedirectAttributes redirectAttributes,
                          Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/product/addProduct";
        } else {
            bookService.save(book);
            return "redirect:/admin/product/add";
        }
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);
        return "admin/product/updateProduct";
    }

    @PostMapping("/admin/product/update")
    public String updateBook(@Valid Book book, BindingResult bindingResult,
                          Model model, RedirectAttributes redirectAttributes,
                          Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/product/addProduct";
        } else {
            bookService.updateBook(book);
            return "redirect:/admin/product/add";
        }
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        bookService.delete(id);
        return "redirect:/admin/product/view";
    }

    @PostMapping("/admin/product/search")
    public String searchProduct(@Valid Book book, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes,
                              Authentication authentication, HttpSession session,
                              HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while adding delivery info");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/product/search";
        } else {
//            List<Book> books = bookService.findAllByNameOrLocation(book.getName(),book.getLocation());
            List<Book> books = bookService.search(book.getName());

            model.addAttribute("books", books);
            model.addAttribute("book", book);

            return "admin/product/viewProducts";
        }
    }

    @GetMapping("/admin/product/view")
    public String getOrder(Model model, Authentication authentication,
                           HttpSession session) {
        Book book = new Book();
        List<Book> books = new ArrayList<>();
        model.addAttribute("books", books);
        model.addAttribute("book", book);
        return "admin/product/viewProducts";
    }

}
