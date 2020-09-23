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

import java.util.*;

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
        Book book1 = new Book("Apocalypto", 5.49, "/images/Apocalypto.png","2f");
        Book book2 = new Book("concussion", 15.00, "/images/concussion.png","3f");
        Book book3 = new Book("legends of the fall", 5.9, "/images/legendsofthefall.png","2f");
        Book book4 = new Book("the hunting ground", 4.9, "/images/thehuntingground.png","2f");
        Book book5 = new Book("larry friends", 45.00, "/images/larry.png","8f");
        Book book6 = new Book("time machine", 15.49, "/images/timemachine.png","2f");
        Book book7 = new Book("concussion", 1.00, "/images/concussion.png","4f");
        Book book8 = new Book("legends of the fall", 5.9, "/images/legendsofthefall.png","2f");
        Book book9 = new Book("the hunting ground", 4.9, "/images/thehuntingground.png","2f");
        Book book10 = new Book("larry friends", 45.00, "/images/larry.png","2f");
        Book book11 = new Book("Apocalypto", 50.49, "/images/Apocalypto.png","3f");
        Book book12 = new Book("concussion", 5.00, "/images/concussion.png","2f");
        Book book13 = new Book("legends of the fall", .9, "/images/legendsofthefall.png","2f");
        Book book14 = new Book("the hunting ground", .9, "/images/thehuntingground.png","2f");
        Book book15 = new Book("larry friends", 5.00, "/images/larry.png","1f");
        Book book16 = new Book("time machine", 1.49, "/images/timemachine.png","2f");
        Book book17 = new Book("concussion", 19.00, "/images/concussion.png","9f");
        Book book18 = new Book("legends of the fall", 45.9, "/images/legendsofthefall.png","2f");
        Book book19 = new Book("the hunting ground", 14.9, "/images/thehuntingground.png","2f");
        Book book20 = new Book("larry friends", 4.00, "/images/larry.png","2f");

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        books.add(book7);
        books.add(book8);
        books.add(book9);
        books.add(book10);
        books.add(book11);
        books.add(book12);
        books.add(book13);
        books.add(book14);
        books.add(book15);
        books.add(book16);
        books.add(book17);
        books.add(book18);
        books.add(book19);
        books.add(book10);
        books.add(book20);
        for(Book book:books){
            book.setInventory(100);
            bookService.save(book);
        }

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
