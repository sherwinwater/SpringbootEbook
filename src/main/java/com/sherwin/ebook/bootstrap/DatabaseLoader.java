package com.sherwin.ebook.bootstrap;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.repository.RoleRepository;
import com.sherwin.ebook.service.*;
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
    private CategoryService categoryService;
    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(UserService userService, BookService bookService, CartService cartService, RoleRepository roleRepository, AccountService accountService, CategoryService categoryService) {
        this.userService = userService;
        this.bookService = bookService;
        this.cartService = cartService;
        this.roleRepository = roleRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        addUsersAndRoles();
        addBooks();
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

        Category category1 = new Category("Arts&Literacy");
        Category category2 = new Category("Lifestyle");
        Category category3 = new Category("Science");
        Category category4 = new Category("Society");
        Category category5 = new Category("Law");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        Set<Book> bookList1 = new HashSet<>();
        Set<Book> bookList2 = new HashSet<>();
        Set<Book> bookList3 = new HashSet<>();
        Set<Book> bookList4 = new HashSet<>();
        Set<Book> bookList5 = new HashSet<>();
        bookList1.add(book1);
        bookList1.add(book2);
        bookList1.add(book3);
        bookList1.add(book4);
        bookList2.add(book5);
        bookList2.add(book6);
        bookList2.add(book7);
        bookList2.add(book8);
        bookList3.add(book9);
        bookList3.add(book10);
        bookList3.add(book11);
        bookList3.add(book12);
        bookList4.add(book13);
        bookList4.add(book14);
        bookList4.add(book15);
        bookList4.add(book16);
        bookList5.add(book17);
        bookList5.add(book18);
        bookList5.add(book19);
        bookList5.add(book20);
        category1.setBooks(bookList1);
        category2.setBooks(bookList2);
        category3.setBooks(bookList3);
        category4.setBooks(bookList4);
        category5.setBooks(bookList5);


        book1.setCategory(category1);
        book2.setCategory(category1);
        book3.setCategory(category1);
        book4.setCategory(category1);
        book5.setCategory(category2);
        book6.setCategory(category2);
        book7.setCategory(category2);
        book8.setCategory(category2);
        book9.setCategory(category3);
        book10.setCategory(category3);
        book11.setCategory(category3);
        book12.setCategory(category3);
        book13.setCategory(category4);
        book14.setCategory(category4);
        book15.setCategory(category4);
        book16.setCategory(category4);
        book17.setCategory(category5);
        book18.setCategory(category5);
        book19.setCategory(category5);
        book20.setCategory(category5);

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
