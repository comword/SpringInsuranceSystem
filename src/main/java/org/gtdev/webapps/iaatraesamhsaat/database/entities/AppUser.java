package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(indexes = {@Index(columnList="userName", unique = true),
            @Index(columnList="email", unique = true),
            @Index(columnList="displayName", unique = true)})
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false, unique=true, length = 30)
    private String userName;

    @NotBlank
    @Column(nullable = false, unique=true, length = 100)
    private String email;

    @NotBlank
    @Column(nullable = false, unique=true, length = 30)
    private String displayName;

//    @NotBlank
//    @Column(nullable = false)
//    private String backend;

    @NotBlank
    @Column(nullable = false, length = 32) //MD5
    private String password;

    @NotBlank
    @Column(nullable = false, length = 32)
    private String salt;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    public enum userState {
        initial(0),
        enabled(1),
        disabled(2),
        deleted(3);
        private final int code;
        userState(int code) {
            this.code = code;
        }
        public static userState parse(short id) {
            userState right = null; // Default
            for (userState item : userState.values()) {
                if (item.code==id) {
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
    private short stateId;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "userGroup",
            joinColumns = { @JoinColumn(name = "appuser_id") },
            inverseJoinColumns = { @JoinColumn(name = "appgroup_id") })
    @ToString.Exclude
    private List<AppGroup> groups = new ArrayList<>();

    public userState getUserState () {
        return userState.parse(stateId);
    }

    public void setUserState(userState right) {
        stateId = (short) right.code;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private UserDetails details;
}
