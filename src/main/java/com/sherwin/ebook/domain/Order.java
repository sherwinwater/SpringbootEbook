package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "orders")
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private Billing billing;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private Delivery delivery ;

    @ManyToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    private String status;
    private Double tax;
    private Double deliveryFee;
    private Double totalPrice;

    public void addBook(Book book) {
        this.books.add(book);
        book.getOrders().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getOrders().remove(this);
    }

    public void addBooks(List<Book> books){
        this.books = books;
        for(Book book:books){
            book.getOrders().add(this);
        }
    }
}
