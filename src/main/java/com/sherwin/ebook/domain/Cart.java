package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
//@ToString
@Getter
@Setter
public class Cart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne(mappedBy = "cart")
    private User user;

//    @NonNull
//    @OneToMany(fetch = FetchType.EAGER)
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JoinTable(name = "cart_booklist")
//    @JoinColumn(name = "cart_booklist",nullable = false)
    private List<Book> bookList = new ArrayList<>();

    public Cart(long id) {
        this.id =id;
    }

    private double totalPrice;

    public void addBook(Book book) {
        this.bookList.add(book);
    }
    public void updateBook(Book book, Long id) {
        for(Book one: this.bookList){
            if(one.getId() == id){
                one.setQuantity(book.getQuantity());
                one.setInventory(book.getInventory());
            }
        }
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
