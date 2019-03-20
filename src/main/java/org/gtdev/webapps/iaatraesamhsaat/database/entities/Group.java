package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "is_groups",
        indexes = {@Index(columnList="groupName", unique = true)})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(nullable = false, unique=true, length = 30)
    private String groupName;

    public enum gpPrivilege {
        root(0),
        Administrators(10),
        Managers(20),
        Auditors(30),
        Accountants(40),
        Employees(60),
        Customers(80),
        Guests(100);
        private final int priv;
        gpPrivilege(int priv) {
            this.priv = priv;
        }
    }

    @NotBlank
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "SMALLINT")
    @Type(type = "org.hibernate.type.ShortType")
    private gpPrivilege groupPrivilege;
}
