package com.courses.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Subject extends BaseDomain{
    private String code;
    private String name;
    private double price;
    private boolean status;
    private String note;
    private String image;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private User expert;

    public Subject(String code, String name, double price, boolean status, String note, String image) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.status = status;
        this.note = note;
        this.image = image;
    }

    public Subject() {
    }
}
