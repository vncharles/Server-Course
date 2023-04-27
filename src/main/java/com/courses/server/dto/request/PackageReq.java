package com.courses.server.dto.request;

import lombok.Data;

@Data
public class PackageReq {
    private Long subjectId;
    private String title;
    private String excerpt;
    private Integer duration;
    private String description;
    private Boolean status;
    private Double listPrice;
    private Double salePrice;
}
