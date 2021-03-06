package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne(mappedBy = "cart",cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "cart")
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "carts_books",
            joinColumns = {@JoinColumn(name = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    @OrderBy("id ASC")
    private Set<Book> books = new HashSet<>();

    public Cart(Long id) {
        this.id =id;
    }

    @Transient
    private double totalPrice;

    public void updateBook(Book book, Long id) {
        for(Book one: this.books){
            if(one.getId() == id){
                one.setQuantity(book.getQuantity());
                one.setInventory(book.getInventory());
            }
        }
    }

    public void addBook(Book book) {
        this.books.add(book);
        book.getCarts().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getCarts().remove(this);
    }

    public void removeAll(){
        this.books.clear();
    }

    @Access(AccessType.PROPERTY)
    @Column(name="total_price")
    public double getTotalPrice(){
        totalPrice = 0;
        this.books.forEach(book ->  totalPrice += book.getPrice() *book.getQuantity());
        return totalPrice;
    }
}
