package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Cart;
import com.sherwin.ebook.domain.User;
import com.sherwin.ebook.repository.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final BookService bookService;
    private final UserService userService;

    public CartService(CartRepository cartRepository, BookService bookService, UserService userService) {
        this.cartRepository = cartRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public void save(Cart Cart) {
        cartRepository.save(Cart);
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public Cart get(User user) {
        return cartRepository.findCartByUser(user);
    }

    public Cart getByid(Long id) {
        return cartRepository.findCartById(id);
    }

    public void deleteGuestBook(Long id, Cart cart) {
        Book book = cart.getBooks().stream()
                .filter(book1 -> book1.getId() == id)
                .findAny()
                .orElse(null);
        cart.removeBook(book);
    }

    public void deleteUserBook(Long id, Cart cart) {

        Book bookSelected = cart.getBooks().stream()
                .filter(book1 -> book1.getId() == id)
                .findAny()
                .orElse(null);
        Book book = bookService.get(id);
        book.setInventory(book.getInventory() + bookSelected.getQuantity());
//        book.setQuantity(0L);
        bookService.save(book);
        cart.removeBook(bookSelected);
        cartRepository.save(cart);
    }

    public void addUserBook(long id, int quantity, Cart cart) {
        Book book = bookService.get(id);
        long bookInventory = book.getInventory();

        if (cart.getBooks().stream().anyMatch(b -> b.getId() == id)) {
            bookInventory += book.getQuantity();
            book.setInventory(bookInventory);
            if (bookInventory >= quantity) {
                book.setQuantity(quantity);
                book.setInventory(bookInventory - quantity);
            } else {
                book.setInventory(bookInventory);
            }
            cart.updateBook(book, id);
        } else {
            if (bookInventory >= quantity) {
                book.setQuantity(quantity);
                book.setInventory(bookInventory - quantity);
                cart.addBook(book);
            }
        }
        cartRepository.save(cart);
    }

    public void addGuestBook(long id, int quantity, Cart cart) {

        Book book = bookService.get(id);
        book.setQuantity(quantity);

        if (cart.getBooks().isEmpty()) {
            cart.addBook(book);
        } else {
            if (cart.getBooks().stream().anyMatch(b -> b.getId() == id)) {
                cart.updateBook(book, id);
            } else {
                cart.addBook(book);
            }
        }

    }

    public void updateGuestBook(long id, int quantity, Cart cart) {

        Book book = bookService.get(id);
        book.setQuantity(quantity);
        cart.updateBook(book, id);

    }

    public void updateUserBook(long id, int quantity, Cart cart) {

        Book book = bookService.get(id);
        long bookInventory = book.getInventory();
        bookInventory += book.getQuantity();

        book.setInventory(bookInventory);
        if (bookInventory >= quantity) {
            book.setQuantity(quantity);
            book.setInventory(bookInventory - quantity);
        } else {
            book.setInventory(bookInventory);
        }
        cart.updateBook(book, id);

        bookService.save(book);
        cart.updateBook(book, id);
        this.save(cart);
    }

    public void clearCart(Cart cart) {
        cart.removeAll();
        cartRepository.save(cart);
    }

    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }

}
