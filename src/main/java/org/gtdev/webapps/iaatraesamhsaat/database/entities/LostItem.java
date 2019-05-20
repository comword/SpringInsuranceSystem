package org.gtdev.webapps.iaatraesamhsaat.database.entities;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
//    @JoinColumn
//    private InsuranceClaim insuranceClaim;

    @NotBlank
    @Column(nullable = false)
    private String itemType;

    @NotNull
    @Column(nullable = false)
    private Long TypeId;

    @NotBlank
    @Column(nullable = false)
    private String itemName;

    @NotBlank
    @Column(nullable = false)
    private String itemPrice;

    @NotBlank
    @Column(nullable = false)
    private String itemDescription;

    @NotBlank
    @Column(nullable = false)
    private String contactEmail;

    @NotBlank
    @Column(nullable = false)
    private String url;
    @NotBlank
    @Column(nullable = false)
    private String policy;

}
