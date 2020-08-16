package com.sherwin.ebook.cart;

import com.sherwin.ebook.book.BookService;
import com.sherwin.ebook.customer.Customer;
import com.sherwin.ebook.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;

    @GetMapping("/cart")
    public String getList(Model model){
        Customer customer = customerService.get(1L);
        Cart cart = cartService.get(customer);
        model.addAttribute("cart", cart);
        return "cart/list";
    }

    @GetMapping("/cart/add/{id}")
    public String addBook(@PathVariable long id){
        Customer customer = customerService.get(1L);
        Cart cart = cartService.get(customer);
        cart.addBook(bookService.get(id));
        cartService.save(cart);
        return "redirect:/books";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteBook(@PathVariable long id){
        Customer customer = customerService.get(1L);
        cartService.delete(id,customer);
        return "redirect:/cart";
    }

}
