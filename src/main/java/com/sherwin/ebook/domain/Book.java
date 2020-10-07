package com.sherwin.ebook.domain;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties(value={"carts","orders","favorites","category"})
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

    @NonNull
    private String location;
    private String details;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Cart> carts = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "books", cascade = CascadeType.PERSIST)
    private Set<Favorite> favorites =new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

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
