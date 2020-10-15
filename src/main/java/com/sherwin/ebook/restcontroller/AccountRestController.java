package com.sherwin.ebook.restcontroller;

import com.sherwin.ebook.domain.Account;
import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.AccountService;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class AccountRestController {


    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/favorite/book/{id}")
    public String  addBook(@PathVariable Long id,
                          Authentication authentication, HttpSession session) {
        String hint ="";
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            user = userService.getUserByEmail(user.getEmail()); //get data from db
            Account account = user.getAccount();
            accountService.addFavorite(account, id);
        } else {
            hint ="please login first";
        }
        return hint;
    }

}
