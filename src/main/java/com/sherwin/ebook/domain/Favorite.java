package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Favorite extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "favorite",fetch = FetchType.LAZY)
//    @OrderBy("id ASC")
//    private Set<Book> books = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "favorite_books",
            joinColumns = {@JoinColumn(name = "favorite_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    @OrderBy("id ASC")
    private Set<Book> books = new HashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST,mappedBy = "favorite")
    private Account account;

    public void removeFavorite(Book book){
        this.books.remove(book);
        book.getFavorites().remove(this);
    }

}
