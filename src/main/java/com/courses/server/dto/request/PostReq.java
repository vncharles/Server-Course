package com.courses.server.dto.request;

import lombok.Data;

@Data
public class PostReq {
    private String title;
    private String body;
    private String brefInfo;
    private Long authorId;
    private int status;
    private Long categoryId;
}
