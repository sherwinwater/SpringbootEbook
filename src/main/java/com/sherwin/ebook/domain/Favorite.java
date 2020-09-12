package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import com.sherwin.ebook.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "favorite")
    private Set<Book> books = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    private Account account;

}
