package com.site.rentyuzhne.model;

import com.site.rentyuzhne.model.enums.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Заполните поле заголовок")
    @Email(message = "Поле E-mail не валидно")
    @Column(name = "email", unique = true)
    private String email;


    @Column(name = "numberPhone", unique = true)
    private String numberPhone;

    @NotEmpty(message = "Заполните поле имя")
    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;
    private String activationCode;

    @NotEmpty(message = "Заполните поле пароль")
    @Column(name = "password", length = 1000)
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Flat> flats = new ArrayList<>();
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }


    public boolean isAdmin() {
        return roles.contains(Role.ROLE_ADMIN);
    }

}

