package com.sherwin.ebook.controller;

import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

        return "order/checkout";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getCart();
        model.addAttribute("cart", cart);

        return "order/checkout";
    }

    @PostMapping("/placeorder")
    public String placeOrder(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getCart();
        model.addAttribute("cart", cart);

        return "order/placeorder";
    }

}
