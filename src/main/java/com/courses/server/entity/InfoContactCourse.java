package com.courses.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoContactCourse extends BaseDomain {
    private String fullName;
    private String email;
    private String phone;
    private String note;
    private int status;
}
