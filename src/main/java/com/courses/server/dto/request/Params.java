package com.courses.server.dto.request;

import lombok.Data;
import org.springframework.data.repository.query.Param;

@Data
public class Params {
    private long category;
    private Boolean active;
    private int status;
    private String keyword;
}
