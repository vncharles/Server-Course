package com.courses.server.dto.request;

import lombok.Data;

@Data
public class ManagerSubjectRequest {
    private Long id;
    private boolean status;
    private Long expert;
}
