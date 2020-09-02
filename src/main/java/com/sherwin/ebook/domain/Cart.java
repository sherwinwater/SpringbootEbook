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
    @Column(name = "cart_id")
    private Long id;

    @NonNull
    @OneToOne(mappedBy = "cart",cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "cart",fetch = FetchType.EAGER)  //eager
    private List<Book> bookList = new ArrayList<>();

    public Cart(long id) {
        this.id =id;
    }

    @Transient
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

    @Access(AccessType.PROPERTY)
    @Column(name="total_price")
    public double getTotalPrice(){
        totalPrice = 0;
        bookList.forEach(book ->  totalPrice += book.getPrice() *book.getQuantity());
        return totalPrice;
    }
}
