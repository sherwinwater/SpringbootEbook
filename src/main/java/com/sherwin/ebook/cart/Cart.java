package com.sherwin.ebook.cart;

import com.sherwin.ebook.book.Book;
import com.sherwin.ebook.config.Auditable;
import com.sherwin.ebook.customer.Customer;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Cart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "cart_booklist")
    private List<Book> bookList;

    public void addBook(Book book) {
        if (!bookList.contains(book))
            this.bookList.add(book);
    }

    public void removeBook(Book book) {
        this.bookList.remove(book);
    }
}
