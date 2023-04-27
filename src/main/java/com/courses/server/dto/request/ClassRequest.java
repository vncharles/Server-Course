package com.courses.server.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class ClassRequest {
    private String code;
    private long packages;
    private Date dateFrom;
    private Date dateTo;
    private boolean status;
    private long trainer;
    private String branch;
    private String schedule;
    private String time;
    private Boolean online;
    private long supporterId;
}
