package com.courses.server.dto.request;

import lombok.Data;

@Data
public class ExpertRequest {
    private String description;
    private String username;
    private String fullname;
    private String Phone;
    private String company;
    private String jobTitle;
    private Boolean status;
    private Long userId;
}
