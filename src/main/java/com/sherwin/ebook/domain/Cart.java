package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
//@ToString
@Getter
@Setter
public class Cart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne
    private User user;

    @NonNull
    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "cart_booklist")
    private List<Book> bookList;

    public Cart(long id) {
        this.id =id;
    }

    private double totalPrice;

    public void addBook(Book book) {
        this.bookList.add(book);
    }

    public void removeBook(Book book) {
        this.bookList.remove(book);
    }

    public double getTotalPrice(){
        totalPrice = 0;
        bookList.forEach(book ->  totalPrice += book.getPrice() *book.getQuantity());
        return totalPrice;
    }
}
