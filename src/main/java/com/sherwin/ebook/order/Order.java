package com.sherwin.ebook.order;

import com.sherwin.ebook.book.Book;
import com.sherwin.ebook.cart.Cart;
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
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    private Customer customer;

//    private Cart cart;

}
