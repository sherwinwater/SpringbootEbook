package com.sherwin.ebook.cart;

import com.sherwin.ebook.book.Book;
import com.sherwin.ebook.book.BookService;
import com.sherwin.ebook.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService{

    private final CartRepository cartRepository;
    private final BookService bookService;

    public CartService(CartRepository cartRepository, BookService bookService) {
        this.cartRepository = cartRepository;
        this.bookService = bookService;
    }

    public void save(Cart Cart) {
        cartRepository.save(Cart);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart get(Customer customer) {
        return cartRepository.findCartByCustomer(customer);
    }

    public Cart getByid(Long id ) {
        return cartRepository.findCartById(id);
    }

    public void delete(Long id, Customer customer) {
        Cart cart = cartRepository.findCartByCustomer(customer);
        Book book = bookService.get(id);
        cart.removeBook(book);
        cartRepository.save(cart);
    }

}
