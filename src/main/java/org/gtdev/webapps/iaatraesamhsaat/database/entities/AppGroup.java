package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "is_groups",
        indexes = {@Index(columnList="groupName", unique = true)})
@Data
public class AppGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(nullable = false, unique=true, length = 30)
    private String groupName;

    public enum gpPrivilege {
        ROLE_root(0),
        ROLE_Administrators(10),
        ROLE_Managers(20),
        ROLE_Auditors(30),
        ROLE_Accountants(40),
        ROLE_Employees(60),
        ROLE_Customers(80),
        ROLE_Guests(100);
        private final int priv;
        gpPrivilege(int priv) {
            this.priv = priv;
        }
        public static gpPrivilege parse(short id) {
            gpPrivilege right = null; // Default
            for (gpPrivilege item : gpPrivilege.values()) {
                if (item.priv==id) {
                    right = item;
                    break;
                }
            }
            return right;
        }
    }

    @NotBlank
    @Column(nullable = false, columnDefinition = "SMALLINT")
    @Type(type = "org.hibernate.type.ShortType")
    private short gpPrivilegeId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "groups")
    private Set<AppUser> posts = new HashSet<>();

    public gpPrivilege getGroupPrivilege () {
        return gpPrivilege.parse(gpPrivilegeId);
    }

    public void setGroupPrivilege(gpPrivilege right) {
        gpPrivilegeId = (short) right.priv;
    }
}
