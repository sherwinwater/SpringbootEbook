package com.sherwin.ebook.domain.account;

import com.sherwin.ebook.config.Auditable;
import com.sherwin.ebook.domain.Billing;
import com.sherwin.ebook.domain.Book;
import com.sherwin.ebook.domain.Order;
import com.sherwin.ebook.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Account extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Billing billing;

    @OneToOne(mappedBy = "account")
    private User user;
}
