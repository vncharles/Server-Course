package com.courses.server.dto.request;

import lombok.Data;

@Data
public class ManagerSubjectRequest {
    private String code;
    private boolean status;
    private String expert;
}
