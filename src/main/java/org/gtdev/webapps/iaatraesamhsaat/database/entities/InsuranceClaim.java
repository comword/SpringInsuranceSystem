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

    @ManyToOne
    @JoinColumn
    private AppUser user;

    @NotBlank
    @Column(nullable = false)
    private String claimStep;

    @Column
    private String result;

    @OneToOne
    @JoinColumn
    private LostItem lostItem;

    @Column(nullable = false)
    private String date;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

}
