package com.courses.server.dto.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    private String body;
    private String email;
    private int vote;
    private Long expertId;
    private Long packageId;
    private Long comboId;
    private Long blogId;
    private Long classId;
}
