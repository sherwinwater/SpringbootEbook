package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;
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
    @JoinColumn(unique = true)
    private User user;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Billing billing;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Delivery delivery ;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order",fetch = FetchType.EAGER)  //eager
    private List<Book> books = new ArrayList<>();

    private String status;
    private Double tax;
    private Double deliveryFee;
    private Double totalPrice;

}
