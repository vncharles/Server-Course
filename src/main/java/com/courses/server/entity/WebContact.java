package com.courses.server.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "web_contact")
public class WebContact extends BaseDomain {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String message;
    private String note;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "setting_id")
    private Setting category;

    @ManyToOne
    @JoinColumn(name = "supporter_id", referencedColumnName = "id")
    private User supporter;

    public WebContact(String fullName, String email, String phoneNumber, String message, boolean status) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.status = status;
    }

    public WebContact() {
    }
}
