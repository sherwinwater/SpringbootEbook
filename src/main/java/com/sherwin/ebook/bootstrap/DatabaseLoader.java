package com.sherwin.ebook.bootstrap;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Role;
import com.sherwin.ebook.domain.Account;
import com.sherwin.ebook.repository.RoleRepository;
import com.sherwin.ebook.service.AccountService;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private UserService userService;
    private BookService bookService;
    private CartService cartService;
    private RoleRepository roleRepository;
    private AccountService accountService;
    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(UserService userService, BookService bookService, CartService cartService, RoleRepository roleRepository) {
        this.userService = userService;
        this.bookService = bookService;
        this.cartService = cartService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        addUsersAndRoles();
//        addBooks();
    }

    private void addBooks() {
        Book book1 = new Book("Apocalypto", 5.49, "/images/Apocalypto.png");
        Book book2 = new Book("concussion", 15.00, "/images/concussion.png");
        Book book3 = new Book("legends of the fall", 5.9, "/images/legendsofthefall.png");
        Book book4 = new Book("the hunting ground", 4.9, "/images/thehuntingground.png");
        Book book5 = new Book("larry friends", 45.00, "/images/larry.png");
        Book book6 = new Book("time machine", 15.49, "/images/timemachine.png");

        book1.setInventory(100);
        book2.setInventory(200);
        book3.setInventory(300);
        book4.setInventory(400);
        book5.setInventory(500);
        book6.setInventory(600);

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        bookService.save(book4);
        bookService.save(book5);
        bookService.save(book6);
    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("0");

        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        Cart cart3 = new Cart();

        Account account1 =new Account();
        Account account2 =new Account();
        Account account3 =new Account();

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com", secret, true, "Joe", "User", "joedirt");
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        cart1.setUser(user);
        user.setCart(cart1);
        user.setAccount(account1);
        account1.setUser(user);
        userService.save(user);
//        cartService.save(cart1);
        users.put("user@gmail.com", user);

        User admin = new User("sam@sam.com", secret, true,  "sam", "sam", "sam");
        admin.setConfirmPassword(secret);
        admin.addRole(adminRole);
        cart2.setUser(admin);
        admin.setCart(cart2);
        account2.setUser(admin);
        admin.setAccount(account2);
        userService.save(admin);
//        cartService.save(cart2);
        users.put("sam@sam.com", admin);

        User master = new User("super@gmail.com", secret, true,  "Super", "User", "superduper");
        master.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        master.setConfirmPassword(secret);
        cart3.setUser(master);
        master.setCart(cart3);
        account3.setUser(master);
        master.setAccount(account3);
        userService.save(master);
//        cartService.save(cart3);
        users.put("super@gmail.com", master);

    }
}
