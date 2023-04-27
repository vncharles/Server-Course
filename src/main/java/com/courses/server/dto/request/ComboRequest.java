package com.courses.server.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ComboRequest {
    private String title;
    private String description;
    private List<ComboPackageRequest> packages;
}
