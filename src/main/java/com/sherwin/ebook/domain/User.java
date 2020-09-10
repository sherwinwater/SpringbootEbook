package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import com.sherwin.ebook.domain.validator.PasswordsMatch;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
//@ToString
@Getter
@Setter
@PasswordsMatch
@Table(name = "users")
public class User extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 8, max = 20, message = "Email size must be between {min} and {max}.")
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(length = 100)
    private String password;

    @Transient
    @NotEmpty(message = "Please confirm your password.")
    private String confirmPassword;

    @NonNull
    @Column(nullable = false)
    private boolean enabled;

    @OneToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(unique = true)
    private Cart cart;

    //    @NonNull
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
//    @ToString.Exclude
    private Set<Order> orders = new LinkedHashSet<>();

    @NonNull
    @NotEmpty(message = "You must enter First Name.")
    private String firstName;

    @NonNull
    @NotEmpty(message = "You must enter Last Name.")
    private String lastName;

    @Transient
    @Setter(AccessLevel.NONE)
    private String fullName;

    @NonNull
    @NotEmpty(message = "Please enter alias.")
    @Column(nullable = false, unique = true)
    private String alias;

    private String activationCode;

    public User(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @ManyToMany(fetch = FetchType.EAGER)
//    @ManyToMany(cascade = CascadeType.ALL)  // detached entity passed to persist
    @ToString.Exclude
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;

//        return roles.stream().map(
//        role -> new SimpleGrantedAuthority(role.getName()))
//                  .collect(Collectors.toList());
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    @Override
    public String getUsername() {
        return "Hi " + StringUtils.capitalize(alias) + "!";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
