package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Provinces {
    @Id
    @Column(nullable = false, unique=true, length = 6)
    private String code;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String chineseName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String englishName;
}
