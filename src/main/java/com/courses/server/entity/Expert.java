package com.courses.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Expert extends BaseDomain {
    private String company;
    private String jobTitle;
    private boolean status;
    
    @NotEmpty(message = "Nhập 1 vài thông tin miêu tả...")
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;
}
