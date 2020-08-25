package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Delivery extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne
    private Order order;

    @NonNull
    @OneToOne
    private User user;

    private String firstName;
    private String lastName;
    private String address;
    private String country;
    private String state;
    private String zip;

}
