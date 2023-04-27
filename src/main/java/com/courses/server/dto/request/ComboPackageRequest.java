package com.courses.server.dto.request;

import lombok.Data;

@Data
public class ComboPackageRequest {
    private Long packageId;
    private double salePrice;
}
