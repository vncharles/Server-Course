package com.courses.server.dto.response;

import com.courses.server.entity.Class;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ClassDTO {
    private Long id;
    private String code;
    private Date dateFrom;
    private Date dateTo;
    private boolean status;
    private String packages;
    private UserDTO trainer;

    public ClassDTO(Class _class) {
        this.id = _class.getId();
        this.packages = _class.getPackages();
        this.code = _class.getCode();
        this.dateFrom = _class.getDateFrom();
        this.dateTo = _class.getDateTo();
        this.status = _class.isStatus();
        this.trainer = _class.getTrainer() != null ? new UserDTO(_class.getTrainer()) : null;
    }
}
