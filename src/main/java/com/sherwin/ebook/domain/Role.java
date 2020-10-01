package com.sherwin.ebook.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
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