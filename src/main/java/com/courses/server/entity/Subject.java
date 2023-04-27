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
    private boolean status;
    private String note;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "expert_id", referencedColumnName = "id")
    private Expert expert;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "setting_id")
    private Setting category;

    public Subject(String code, String name, boolean status, String note) {
        this.code = code;
        this.name = name;
        this.status = status;
        this.note = note;
    }

    public Subject() {
    }
}
