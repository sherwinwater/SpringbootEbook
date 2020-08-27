package com.sherwin.ebook.controller;

import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String getUserCart(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getCart();
        System.out.println(cart);
        if (cart == null) {
            cart = new Cart();
            user.setCart(cart);
        }
        model.addAttribute("cart", cart);
        return "cart/list";
    }

    @GetMapping("/cart/guest")
    public String getGuestCart(Model model, HttpSession session) {
        User guest = (User) session.getAttribute("guest");
        if (guest == null) {
            guest = new User("guest");
            guest.setCart(new Cart());
            session.setAttribute("guest", guest);
        }
        Cart cart = guest.getCart();
        model.addAttribute("cart", cart);
        return "cart/list";
    }

    @PostMapping("/cart/add/{id}")
    public String addBook(@PathVariable long id, @RequestParam("quantity") int quantity,
                          Authentication authentication, HttpSession session) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Cart cart = user.getCart();
            System.out.println(cart);
            if (cart == null) {
                cart = new Cart();
                user.setCart(cart);
            }
            cartService.addUserBook(id, quantity, cart);
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
            cartService.addGuestBook(id, quantity, cart);
        }
        return "redirect:/book";
    }

    @PostMapping("/cart/update/{id}")
    public String updateBook(@PathVariable long id, @RequestParam("quantity") int quantity,
                             Authentication authentication, HttpSession session) {

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Cart cart = user.getCart();
            if (cart == null) {
                cart = new Cart();
            }
            cartService.updateUserBook(id, quantity, cart);
            return "redirect:/cart";
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
            cartService.updateGuestBook(id, quantity, cart);
        }

        return "redirect:/cart/guest";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteBook(@PathVariable long id, Authentication authentication, HttpSession session) {

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Cart cart = user.getCart();
            if (cart == null) {
                cart = new Cart();
            }
            cartService.deleteUserBook(id, cart);
            return "redirect:/cart";
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
            cartService.deleteGuestBook(id, cart);
            return "redirect:/cart/guest";
        }
    }

}
