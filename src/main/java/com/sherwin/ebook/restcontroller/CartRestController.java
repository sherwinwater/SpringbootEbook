package com.sherwin.ebook.restcontroller;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;


    @GetMapping("/cart")
    public int getBookNumber(
                        Authentication authentication, HttpSession session) {
        int bookNumber =0;
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Cart cart = cartService.get(user);  //get data from db
            bookNumber = cart.getBooks().size();
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
            bookNumber = cart.getBooks().size();
        }
        return bookNumber;
    }

 @GetMapping("/cart/book/{id}")
    public int addBook(@PathVariable Long id,
                        Authentication authentication, HttpSession session) {
        int bookNumber =0;
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Cart cart = cartService.get(user);  //get data from db
            cartService.addUserBook(id, 1, cart);
            bookNumber = cart.getBooks().size();
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
            cartService.addGuestBook(id, 1, cart);
            bookNumber = cart.getBooks().size();
        }
        System.out.println(bookService.get(id));
        return bookNumber;
    }

    @GetMapping("/cart/book/update/{id}/{quantity}")
    public int updateBook(@PathVariable Long id, @PathVariable int quantity,
                             Authentication authentication, HttpSession session) {

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
//            Cart cart = user.getCart();
            Cart cart = cartService.get(user);  //get data from db
            cartService.updateUserBook(id, quantity+1, cart);
            return quantity;
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
            cartService.updateGuestBook(id, quantity+1, cart);
        }

        return quantity+1;
    }

    @GetMapping("/cart/book/delete/{id}")
    public long deleteBook(@PathVariable Long id, Authentication authentication, HttpSession session) {

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Cart cart = cartService.get(user);  //get data from db
//            Cart cart = user.getCart();
            cartService.deleteUserBook(id, cart);
            return id;
        } else {
            User guest = (User) session.getAttribute("guest");
            if (guest == null) {
                guest = new User("guest");
                session.setAttribute("guest", guest);
                guest.setCart(new Cart());
            }
            Cart cart = guest.getCart();
            cartService.deleteGuestBook(id, cart);
            return id;
        }
    }

}
