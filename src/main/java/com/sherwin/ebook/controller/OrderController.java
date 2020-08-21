package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/order")
    public String getList(Model model) {

        return "order/list";
    }

}
