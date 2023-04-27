package com.courses.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Customer extends BaseDomain{
    private String fullName;
    private String email;
    private String mobile;
}
