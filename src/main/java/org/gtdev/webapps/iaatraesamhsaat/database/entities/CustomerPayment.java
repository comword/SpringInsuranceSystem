package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class CustomerPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private AppUser user;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String payMethod;

    @NotBlank
    @Column(nullable = false, length = 22)
    private String cardName;

    @NotBlank
    @Column(nullable = false, unique = true, length = 19)
    private String cardNumber;

    @NotBlank
    @Column(nullable = false, length = 5)
    private String cardExpiration;

    @NotBlank
    @Column(nullable = false, length = 4)
    private String cardCvv;
}
