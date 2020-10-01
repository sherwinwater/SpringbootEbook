package com.sherwin.ebook.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Billing extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(mappedBy = "billing")
//    @OneToOne(mappedBy = "billing",cascade = CascadeType.ALL, orphanRemoval = true)
//    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String country;
    private String state;
    private String zip;

    @Override
    public String toString() {
        return "Billing{" +
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
