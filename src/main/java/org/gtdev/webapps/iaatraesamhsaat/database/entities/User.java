package org.gtdev.webapps.iaatraesamhsaat.database.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "is_users",
        indexes = {@Index(columnList="userName", unique = true),
            @Index(columnList="email", unique = true),
            @Index(columnList="displayName", unique = true)})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    }

    @NotBlank
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "SMALLINT")
    @Type(type = "org.hibernate.type.ShortType")
    private userState state;

}
