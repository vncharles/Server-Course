package com.courses.server.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class TraineeRequest {
    private String email;
    private String fullname;
    private String phone;
    private Boolean status;
    private Date dropOutDate;
    private Long classId;
    private Long userId;
}
