package br.com.jafethenrique.JavaChallenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name="User")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true)
    private String email;

    private String password;

    @Column(name="created", updatable = false, nullable = false)
    private OffsetDateTime created;

    @Column(name="last_login", nullable = false)
    private OffsetDateTime lastLogin;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userModel")
    private List<Phones> phones;

}
