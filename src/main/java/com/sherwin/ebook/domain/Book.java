package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private Double price;

    @NonNull
    private String photoUrl;

    private long inventory;
    private long quantity;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
//    private List<Cart> carts = new ArrayList<>();
    private Set<Cart> carts = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();

}
