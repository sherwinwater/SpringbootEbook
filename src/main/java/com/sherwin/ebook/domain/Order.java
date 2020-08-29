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
    @OneToOne
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String status;
    private Double tax;
    private Double deliveryFee;
    private Double totalPrice;

}
