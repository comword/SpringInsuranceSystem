package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class CustomerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "details")
    private AppUser user;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String firstName;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String lastName;

    @NotBlank
    @Column(nullable = false, unique=true, length = 20)
    private String phoneNumber;

    @NotBlank
    @Column(nullable = false, unique=true, length = 20)
    private String idNumber;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String address;

    @Column(length = 120)
    private String address2;

    @NotBlank
    @Column(nullable = false, length = 6)
    private String province;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String zipCode;
}
