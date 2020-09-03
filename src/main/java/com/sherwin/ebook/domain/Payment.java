package com.sherwin.ebook.domain;

import com.sherwin.ebook.config.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Payment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne
    @JoinColumn(unique = true)
    private Billing billing;

    private String fullName;
    private String CreditCardNumber;
    private String Expiration;
    private String CVV;
    private String paymentType;

}
