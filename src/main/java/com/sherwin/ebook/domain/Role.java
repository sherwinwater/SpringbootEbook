package com.sherwin.ebook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @ManyToMany( mappedBy = "roles",cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();
//    private Set<User> users = new HashSet<>();

}