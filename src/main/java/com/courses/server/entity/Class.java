package com.courses.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Class extends BaseDomain{
    private String packages;
    private String code;
    private Date dateFrom;
    private Date dateTo;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

}
