package com.mockable.app.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(
        name = "account_table",
       uniqueConstraints = {
                @UniqueConstraint(columnNames = {"accountId"})
       }
)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_type")
    private String accountType = "loan";

//    @Column(name = "account_number")
//    private String accountNumber;

    @Column(name = "response_data")
    private String responseData;

}
