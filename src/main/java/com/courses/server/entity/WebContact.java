package com.courses.server.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "web_contact")
public class WebContact extends BaseDomain {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String message;
    private boolean status;

    public WebContact(String fullName, String email, String phoneNumber, String address, String message, boolean status) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.message = message;
        this.status = status;
    }

    public WebContact() {
    }
}
