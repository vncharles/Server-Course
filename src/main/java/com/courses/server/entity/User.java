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

    @Column(nullable = false)
    private String username;

    @NotNull
    private String password;
    private String fullname;
    private String phoneNumber;
    private String avatar;
    private String note;

    private boolean active;

    @Column(name = "register_token")
    private String registerToken;
    private LocalDateTime timeRegisterToken;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_account")
    private ETypeAccount type_account;

    @OneToOne()
    @JoinColumn(name = "role_id", referencedColumnName = "setting_id")
    private Setting role;

    public User(String email, String username, String password,String phone, boolean active) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.active = active;
        this.phoneNumber = phone;
    }

    public User() {
        active = false;
    }
}
