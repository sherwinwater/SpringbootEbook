package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.PERSIST)
//    @OneToOne
    private Billing billing;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Delivery delivery;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "orders_books",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private Set<Book> books = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Cart cart;

    private String status;
    @Column(precision = 10, scale =2, columnDefinition="DECIMAL(10,2)")
    private Double tax;
    @Column(precision = 10, scale =2, columnDefinition="DECIMAL(10,2)")
    private Double deliveryFee;
    @Column(precision = 10, scale =2, columnDefinition="DECIMAL(10,2)")
    private Double totalPrice;

    public void addBook(Book book) {
        this.books.add(book);
        book.getOrders().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getOrders().remove(this);
    }

    public void addBooks(Set<Book> books) {
        this.books = books;
        for (Book book : books) {
            book.getOrders().add(this);
        }
    }

    public void addUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", status='" + status + '\'' +
                ", tax=" + tax +
                ", deliveryFee=" + deliveryFee +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
