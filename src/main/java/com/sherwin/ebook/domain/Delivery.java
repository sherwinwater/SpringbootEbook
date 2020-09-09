package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Delivery extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL)
    private Order order;

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String country;
    private String state;
    private String zip;

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
