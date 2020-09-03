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
    @JoinColumn(unique = true)
    private Order order;

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String country;
    private String state;
    private String zip;

}
