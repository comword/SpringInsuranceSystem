package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String chineseName;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String englishName;
}
