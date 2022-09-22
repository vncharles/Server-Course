package com.courses.server.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity @Data
@Table(	name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends BaseDomain{

    @Column(updatable = false, nullable = false)
    private String email;

    @Column(updatable = false, nullable = false)
    private String username;

    @NotNull
    private String password;

//    private String fullname;
//    private String phoneNumber;
//    private String imageUrl;

    private boolean active;

    @Column(name = "register_token")
    private String registerToken;
    private LocalDateTime timeRegisterToken;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_account")
    private ETypeAccount type_account;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String email, String username, String password, boolean active) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public User() {
        active = false;
    }
}
