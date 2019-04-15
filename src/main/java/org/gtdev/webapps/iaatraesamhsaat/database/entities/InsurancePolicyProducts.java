package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
public class InsurancePolicyProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String insuranceName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String insuranceChineseName;

    @Column(columnDefinition = "TINYTEXT")
    private String agreementPath;

    @Column(columnDefinition = "TEXT")
    private String insuranceAbstract;

    @Column(columnDefinition = "TEXT")
    private String insuranceAbstractChinese;
}
