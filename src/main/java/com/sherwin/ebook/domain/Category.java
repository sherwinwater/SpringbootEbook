package com.sherwin.ebook.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Category extends Auditable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "category")
    @OrderBy("id ASC")
    private Set<Book> books = new LinkedHashSet<>();

}
