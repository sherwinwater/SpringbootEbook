package com.sherwin.ebook.controller;

import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getList(Model model) {
        Optional<User> userOptional = userService.getByid(1L);
        User user = userOptional.get();
        Optional<Cart> cartOptional = Optional.ofNullable(user.getCart());
        Cart cart = cartOptional.get();
        model.addAttribute("cart", cart);
        return "cart/list";
    }

    @PostMapping("/cart/add/{id}")
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public void  addBook(@PathVariable long id, @RequestParam("quantity") int quantity){
//    public ResponseEntity addBook(@PathVariable long id, @RequestParam("quantity") int quantity){
    public String addBook(@PathVariable long id, @RequestParam("quantity") int quantity) {
        cartService.addBook(id,quantity);
        return "redirect:/book";
//            return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/cart/update/{id}")
    public String updateBook(@PathVariable long id, @RequestParam("quantity") int quantity) {
        cartService.updateBook(id,quantity);
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
