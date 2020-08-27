package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "user_order")
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne(cascade = {CascadeType.ALL})
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(cascade = {CascadeType.ALL},mappedBy = "order", orphanRemoval = true)
    private Billing billing;

    @OneToOne(cascade = {CascadeType.ALL},mappedBy = "order", orphanRemoval = true)
    private Delivery delivery;

    @OneToOne(cascade = {CascadeType.ALL},mappedBy = "order", orphanRemoval = true)
    private Payment payment;

    private String status;
    private Double tax;
    private Double deliveryFee;
    private Double totalPrice;

}
