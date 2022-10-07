package com.courses.server.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class ClassRequest {
    private String packages;
    private Date dateFrom;
    private Date dateTo;
    private boolean status;
    private String trainer;
}
