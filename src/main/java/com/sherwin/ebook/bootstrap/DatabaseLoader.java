package com.sherwin.ebook.bootstrap;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.service.BookService;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.service.CartService;
import com.sherwin.ebook.domain.Customer;
import com.sherwin.ebook.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private CustomerService customerService;
    private BookService bookService;
    private CartService cartService;

    public DatabaseLoader(CustomerService customerService, BookService bookService, CartService cartService) {
        this.customerService = customerService;
        this.bookService = bookService;
        this.cartService = cartService;
    }

    @Override
    public void run(String... args) throws Exception {
        Customer one = new Customer("sam","s","sam@sam.com","images/Apocalypto.png");
        Customer two = new Customer("jack","jack","jakc@sam.com","images/concussion.png");
        Customer three = new Customer("tom","tomes","tom@sam.com","images/legendsofthefall.png");
        Customer four = new Customer("andy","s","andy@sam.com","images/thehuntingground.png");
        Customer five = new Customer("joe","s","joe@sam.com","images/larry.png");
        Customer six = new Customer("thomas","s","tomas@sam.com","images/timemachine.png");

//        one.setDate(LocalDateTime.now());
        customerService.save(one);
        customerService.save(two);
        customerService.save(three);
        customerService.save(four);
        customerService.save(five);
        customerService.save(six);

        Book book1 = new Book("Apocalypto",45.49,"images/Apocalypto.png");
        Book book2 = new Book("concussion",45.49,"images/concussion.png");
        Book book3 = new Book("legends of the fall",45.49,"images/legendsofthefall.png");
        Book book4 = new Book("the hunting ground",45.49,"images/thehuntingground.png");
        Book book5 = new Book("larry friends",45.49,"images/larry.png");
        Book book6 = new Book("time machine",45.49,"images/timemachine.png");

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

        Customer customer = customerService.get(1L);
        Cart cart = new Cart();
        cart.setCustomer(customer);
//        List<Book> bookList = new ArrayList<>();
//        bookList.add(book1);
//        cart.setBookList(bookList);
//        cart.addBook(book2);
//        book1.setCart(cart);
        cartService.save(cart);

    }
}
