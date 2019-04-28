package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
public class InsuranceClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn
    private InsurancePolicyRecord policy;

    @NotBlank
    @ManyToOne(optional = false)
    @JoinColumn
    private AppUser user;

    @Column(nullable = false)
    private int claimStep;

    @Column
    private String result;

    @OneToOne
    @JoinColumn
    private LostItem lostItem;

    @NotBlank
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateTime;
}
