package com.sherwin.ebook.cart;

import com.sherwin.ebook.book.Book;
import com.sherwin.ebook.book.BookService;
import com.sherwin.ebook.customer.Customer;
import com.sherwin.ebook.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;

    @GetMapping("/cart")
    public String getList(Model model) {
        Customer customer = customerService.get(1L);
        Optional<Cart> cartOptional = cartService.get(customer);
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
        return "redirect:/books";
//            return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/cart/update/{id}")
    public String updateBook(@PathVariable long id, @RequestParam("quantity") int quantity) {
        cartService.updateBook(id,quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        Customer customer = customerService.get(1L);
        cartService.delete(id, customer);
        return "redirect:/cart";
    }

}
