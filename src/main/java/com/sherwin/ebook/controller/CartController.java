package com.sherwin.ebook.controller;

import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/cart")
    public String getUserCart(Model model,  Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        Cart cart = user.getCart();
//        System.out.println(cart.getBookList().isEmpty());
//        System.out.println(cart.getBookList() == null);
        model.addAttribute("cart", cart);
        return "cart/list";
    }

    @GetMapping("/cart/guest")
    public String getGuestCart(Model model) {
        User one = new User("guest");
        one.setCart(new Cart());
//        System.out.println(one.getCart().getBookList() == null);
        model.addAttribute("cart", one.getCart());
        return "cart/guestlist";
    }

    @PostMapping("/cart/add/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public void  addBook(@PathVariable long id, @RequestParam("quantity") int quantity){
//    public ResponseEntity addBook(@PathVariable long id, @RequestParam("quantity") int quantity){
    public String addBook(@PathVariable long id, @RequestParam("quantity") int quantity,
                          Authentication authentication) {

        // guest--> addGuestBook

        //user--addBook
        User user = (User)authentication.getPrincipal();
        Cart cart = user.getCart();
        cartService.addUserBook(id, quantity,cart);
        return "redirect:/book";
//            return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/cart/update/{id}")
    public String updateBook(@PathVariable long id, @RequestParam("quantity") int quantity,
                             Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        Cart cart = user.getCart();
        cartService.updateBook(id, quantity,cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        Optional<User> userOptional = userService.getByid(1L);
        User user = userOptional.get();
        cartService.delete(id, user);
        return "redirect:/cart";
    }

}
