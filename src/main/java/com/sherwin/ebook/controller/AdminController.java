package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.Account;
import com.sherwin.ebook.domain.Billing;
import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

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

    @RequestMapping("/admin/user/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getByid(id).get());
        return "user/view";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        Optional<User> userOptional = userService.getByid(id);
        User user = userOptional.get();
        model.addAttribute("user", user);
        return "admin/userform";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/users";
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

    @GetMapping("/admin/product/add")
    public String addProductHome(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
        Book book = new Book();
        model.addAttribute("book", book);

        return "admin/productsInsert";
    }

    @PostMapping("/admin/product/add")
    public String getBilling(@Valid Book book, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            logger.info("Validation errors were found while registering a new user");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin/productsInsert";
        } else {
            User user = (User) authentication.getPrincipal();
            user = userService.getUserByEmail(user.getEmail()).get(); //get data from db
            Account account = user.getAccount();
            bookService.save(book);
            return "redirect:/admin/product/add";
        }
    }

}
