package com.sherwin.ebook.bootstrap;

import com.sherwin.ebook.domain.*;
import com.sherwin.ebook.repository.RoleRepository;
import com.sherwin.ebook.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

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
//        addUsersAndRoles();
//        addBooks();
    }

    private void addBooks() {
        Book book1 = new Book("Beiginning Hibernate", 5.49, "/images/BeginningHibernate.jpeg","2f");
        Book book2 = new Book("Building Restful Web Service With Java8", 15.00, "/images/BuildingRestfulWebServiceWithJava8.jpeg","3f");
        Book book3 = new Book("Clean Code", 5.9, "/images/cleanCode.jpeg","2f");
        Book book4 = new Book("Core Java 11 Fundamentals", 4.9, "/images/CoreJava11Fundamentals.jpeg","2f");
        Book book5 = new Book("Effective Java", 45.00, "/images/EffectiveJava.jpeg","8f");
        Book book6 = new Book("Eloquent JavaScript", 15.49, "/images/EloquentJavaScript.jpeg","2f");
        Book book7 = new Book("Head First Java", 1.00, "/images/HeadFirstJava.jpeg","4f");
        Book book8 = new Book("Head First PHP and MySQL", 5.9, "/images/HeadFirstPHPandMySQL.jpeg","2f");
        Book book9 = new Book("Head First JavaScript Programming", 4.9, "/images/HeadFirstJavaScriptProgramming.jpeg","2f");
        Book book10 = new Book("Head First Patterns", 45.00, "/images/HeadFirstPatterns.jpeg","2f");
        Book book11 = new Book("Hibernate Search in Action", 50.49, "/images/HibernateSearchInAction.jpeg","3f");
        Book book12 = new Book("High Performance MySQL", 5.00, "/images/HighPerformanceMySQL.jpeg","2f");
        Book book13 = new Book("Java8 Fundamentals", .9, "/images/Java8Fundamentals.jpeg","2f");
        Book book14 = new Book("Java Coding Problems", .9, "/images/JavaCodingProblems.jpeg","2f");
        Book book15 = new Book("Java Cook book", 5.00, "/images/JavaCookbook.jpeg","1f");
        Book book16 = new Book("Java First Step", 1.49, "/images/JavaFirstStep.png","2f");
        Book book17 = new Book("Java Hibernate Cookbook", 19.00, "/images/JavaHibernateCookbook.jpeg","9f");
        Book book18 = new Book("JavaScript Everywhere", 45.9, "/images/JavaScriptEverywhere.jpeg","2f");
        Book book19 = new Book("JavaScript the Definitive Guide", 14.9, "/images/JavaScriptTheDefinitiveGuide.jpeg","2f");
        Book book20 = new Book("JavaScript and JQuery Interactive", 4.00, "/images/JavaScriptandJQueryinteractive.jpeg","2f");
        Book book21 = new Book("Just Hibernate", 4.00, "/images/JustHibernate.jpeg","2f");
        Book book22 = new Book("Mastering Hibernate", 4.00, "/images/MasteringHibernate.jpeg","2f");
        Book book23 = new Book("Mastering Spring Cloud", 4.00, "/images/MasteringSpringCloud.jpeg","2f");
        Book book24 = new Book("MySQL8 CookBook", 4.00, "/images/MySQL8CookBook.jpeg","2f");
        Book book25 = new Book("OracleSQL by Example", 4.00, "/images/OracleSQLbyExample.jpeg","2f");
        Book book26 = new Book("Practical OracleSQL", 4.00, "/images/PracticalOracleSQL.jpeg","2f");
        Book book27 = new Book("Pro Hibernate and MongoDB", 4.00, "/images/ProHibernateandMongoDB.jpeg","2f");
        Book book28 = new Book("REST API Development with NodeJS", 4.00, "/images/RESTAPIDevelopmentwithNodeJS.jpeg","2f");
        Book book29 = new Book("React Projects", 4.00, "/images/ReactProjects.jpeg","2f");
        Book book30 = new Book("React Redux in Action", 4.00, "/images/ReactReduxinAction.jpeg","2f");
        Book book31 = new Book("Rest API Design RuleBook", 4.00, "/images/RestAPIDesignRuleBook.jpeg","2f");
        Book book32 = new Book("Restful Web Services", 4.00, "/images/RestfulWebServices.jpeg","2f");
        Book book33 = new Book("Spring Boot2 Recipes", 4.00, "/images/SpringBoot2Recipes.jpeg","2f");
        Book book34 = new Book("Spring Boot in Action ", 4.00, "/images/SpringBootInAction.jpeg","2f");
        Book book35 = new Book("Spring Boot Persistence Best Practice", 4.00, "/images/SpringBootPersistenceBestPractice.jpeg","2f");
        Book book36 = new Book("Spring Microservice in Action", 4.00, "/images/SpringMicroserviceInAction.jpeg","2f");
        Book book37 = new Book("Spring Quick Reference Guide", 4.00, "/images/SpringQuickReferenceGuide.jpeg","2f");
        Book book38 = new Book("Java The Complete Reference", 4.00, "/images/JavaTheCOmpleteReference.jpeg","2f");
        Book book39 = new Book("The React Workshop", 4.00, "/images/TheReactWorkshop.jpeg","2f");
        Book book40 = new Book("Microservice", 4.00, "/images/Microservice.jpeg","2f");


        Category category1 = new Category("Java");
        Category category2 = new Category("JavaScript");
        Category category3 = new Category("SpringBoot");
        Category category4 = new Category("Database");
        Category category5 = new Category("Microservice");

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
        bookList1.add(book3);
        bookList1.add(book4);
        bookList1.add(book5);
        bookList1.add(book7);
        bookList1.add(book10);
        bookList1.add(book13);
        bookList1.add(book14);
        bookList1.add(book15);
        bookList1.add(book16);
        bookList1.add(book17);
        bookList1.add(book38);

        bookList2.add(book6);
        bookList2.add(book9);
        bookList2.add(book18);
        bookList2.add(book19);
        bookList2.add(book20);
        bookList2.add(book29);
        bookList2.add(book30);
        bookList2.add(book39);

        bookList3.add(book23);
        bookList3.add(book33);
        bookList3.add(book34);
        bookList3.add(book35);
        bookList3.add(book37);

        bookList4.add(book1);
        bookList4.add(book8);
        bookList4.add(book11);
        bookList4.add(book12);
        bookList4.add(book21);
        bookList4.add(book22);
        bookList4.add(book24);
        bookList4.add(book25);
        bookList4.add(book26);
        bookList4.add(book27);

        bookList5.add(book2);
        bookList5.add(book28);
        bookList5.add(book31);
        bookList5.add(book32);
        bookList5.add(book36);
        bookList5.add(book40);


        category1.setBooks(bookList1);
        category2.setBooks(bookList2);
        category3.setBooks(bookList3);
        category4.setBooks(bookList4);
        category5.setBooks(bookList5);
        categoryService.save(category1);
        categoryService.save(category2);
        categoryService.save(category3);
        categoryService.save(category4);
        categoryService.save(category5);

        for(Book book:bookList1){
            book.setCategory(category1);
        }
        for(Book book:bookList2){
            book.setCategory(category2);
        }
        for(Book book:bookList3){
            book.setCategory(category3);
        }
        for(Book book:bookList4){
            book.setCategory(category4);
        }
        for(Book book:bookList5){
            book.setCategory(category5);
        }

        List<Book> books = new ArrayList<>();
        Stream.of(bookList1,bookList2,bookList3,bookList4,bookList5).forEach(books::addAll);
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
