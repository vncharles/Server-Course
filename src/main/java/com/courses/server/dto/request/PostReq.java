package com.courses.server.dto.request;

import lombok.Data;

@Data
public class PostReq {
    private String title;
    private String body;
    private Long authorId;
    private String thumnailUrl;
    private int status;
}
