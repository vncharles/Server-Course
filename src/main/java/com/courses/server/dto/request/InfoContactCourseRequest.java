package com.courses.server.dto.request;

import lombok.Data;

@Data
public class InfoContactCourseRequest {
    private String fullName;
    private String email;
    private String phone;
    private String note;
    private Integer status;
    private Long classId;
    private String codeCoupon;
}
