package com.sherwin.ebook.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sherwin.ebook.config.Auditable;
import com.sherwin.ebook.domain.*;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Account extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Billing billing;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Delivery delivery;

    @OneToOne(mappedBy = "account")
    private User user;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Favorite favorite;
}
