package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class InsuranceClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn
    private InsurancePolicyRecord policy;

    @ManyToOne(optional = false)
    @JoinColumn
    private AppUser user;


}
