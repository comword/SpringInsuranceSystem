package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class InsurancePolicyRecord {
    @Id
    @Column(nullable = false, length = 20)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn
    private CustomerDetails customer;

    @ManyToOne(optional = false)
    @JoinColumn
    private InsurancePolicyProducts insuranceProduct;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
    private List<InsuranceClaim> claims;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startDatetime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endDatetime;

    @NotNull
    @Column(nullable = false)
    private float totalPaid;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String destination;
}
