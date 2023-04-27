package com.courses.server.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class NewClassRequest {
    private Long supporterId;
    private Long classId;
}
