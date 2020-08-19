package com.sherwin.ebook.customer;

import com.sherwin.ebook.book.Book;
import com.sherwin.ebook.config.Auditable;
import com.sherwin.ebook.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String emailId;

    @NonNull
    private String photoUrl;

//    @OneToMany(fetch = FetchType.EAGER)
////    @JoinTable(name = "cart_booklist")
//    private List<Order> orderList;

//    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
//    private LocalDateTime Date;
//
//    //    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    public LocalDateTime getDate() {
//        return Date;
//    }
//
//    public void setDate(LocalDateTime date) {
//        Date = date;
//    }


//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    @Column(name = "first_name", nullable = false)
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    @Column(name = "last_name", nullable = false)
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    @Column(name = "email_address", nullable = false)
//    public String getEmailId() {
//        return emailId;
//    }
//
//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }

}
