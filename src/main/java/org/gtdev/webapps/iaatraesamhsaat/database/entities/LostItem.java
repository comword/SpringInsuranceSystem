package org.gtdev.webapps.iaatraesamhsaat.database.entities;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

}
