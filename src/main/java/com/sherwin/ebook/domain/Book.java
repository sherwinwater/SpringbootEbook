package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
//@ToString
@Getter
@Setter
public class Book extends Auditable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Double price;

    @NonNull
    private String photoUrl;

    private Integer inventory;

    private Integer quantity;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Cart> carts = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Favorite favorite;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", photoUrl='" + photoUrl + '\'' +
                ", inventory=" + inventory +
                ", quantity=" + quantity +
                '}';
    }
}
