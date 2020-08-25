package com.sherwin.ebook.bootstrap;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Role;
import com.sherwin.ebook.repository.RoleRepository;
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
    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(UserService userService, BookService bookService, CartService cartService, RoleRepository roleRepository) {
        this.userService = userService;
        this.bookService = bookService;
        this.cartService = cartService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addUsersAndRoles();
        addBooks();
    }

    private void addBooks() {
        Book book1 = new Book("Apocalypto", 45.49, "images/Apocalypto.png");
        Book book2 = new Book("concussion", 45.49, "images/concussion.png");
        Book book3 = new Book("legends of the fall", 45.49, "images/legendsofthefall.png");
        Book book4 = new Book("the hunting ground", 45.49, "images/thehuntingground.png");
        Book book5 = new Book("larry friends", 45.49, "images/larry.png");
        Book book6 = new Book("time machine", 45.49, "images/timemachine.png");

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

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com", secret, true, cart1, "Joe", "User", "joedirt");
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        userService.save(user);
        users.put("user@gmail.com", user);

        User admin = new User("sam@sam.com", secret, true, cart2, "sam", "sam", "sam");
        admin.setConfirmPassword(secret);
        admin.addRole(adminRole);
        userService.save(admin);
        users.put("sam@sam.com", admin);

        User master = new User("super@gmail.com", secret, true, cart3, "Super", "User", "superduper");
        master.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        master.setConfirmPassword(secret);
        userService.save(master);
        users.put("super@gmail.com", master);

        cartService.save(cart1);
        cartService.save(cart2);
        cartService.save(cart3);
    }
}
