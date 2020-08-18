package com.sherwin.ebook.cart;

import com.sherwin.ebook.book.Book;
import com.sherwin.ebook.book.BookService;
import com.sherwin.ebook.customer.Customer;
import com.sherwin.ebook.customer.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BookService bookService;
    private final CustomerService customerService;

    public CartService(CartRepository cartRepository, BookService bookService, CustomerService customerService) {
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.customerService = customerService;
    }

    public void save(Cart Cart) {
        cartRepository.save(Cart);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Optional<Cart> get(Customer customer) {
        return cartRepository.findCartByCustomer(customer);
    }

    public Cart getByid(Long id) {
        return cartRepository.findCartById(id);
    }

    public void delete(Long id, Customer customer) {
        Optional<Cart> cartOptional = cartRepository.findCartByCustomer(customer);
        Cart cart = cartOptional.get();
        Book book = bookService.get(id);
        long bookInventory = book.getInventory();
        book.setInventory(bookInventory + book.getQuantity());
        cart.removeBook(book);
        cartRepository.save(cart);
    }

    public void addBook(long id, int quantity) {
        Customer customer = customerService.get(1L);
        Optional<Cart> cartOptional = cartRepository.findCartByCustomer(customer);
        Cart cart = cartOptional.get();
        Book book = bookService.get(id);
        long bookInventory = book.getInventory();

        if (cart.getBookList().stream().anyMatch(b -> b.getId() == id)) {
            bookInventory += book.getQuantity();
            book.setInventory(bookInventory);
            if (bookInventory >= quantity) {
                book.setQuantity(quantity);
                book.setInventory(bookInventory - quantity);
                bookService.save(book);
                this.save(cart);
            } else {
                book.setInventory(bookInventory);
            }
        } else {
            if (bookInventory >= quantity) {
                book.setQuantity(quantity);
                book.setInventory(bookInventory - quantity);
                cart.addBook(book);
                this.save(cart);
            }
        }

    }

    public void updateBook(long id, int quantity) {
        Customer customer = customerService.get(1L);
        Optional<Cart> cartOptional = cartRepository.findCartByCustomer(customer);
        Cart cart = cartOptional.get();
        Book book = bookService.get(id);
        long bookInventory = book.getInventory();
        bookInventory += book.getQuantity();

        book.setInventory(bookInventory);
        if (bookInventory >= quantity) {
            book.setQuantity(quantity);
            book.setInventory(bookInventory - quantity);
            bookService.save(book);
            this.save(cart);
        } else {
            book.setInventory(bookInventory);
        }
    }

}
