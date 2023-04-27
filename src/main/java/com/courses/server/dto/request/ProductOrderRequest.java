package com.courses.server.dto.request;

import lombok.Data;

@Data
public class ProductOrderRequest {
    private Long packageId;
    private Long comboId;
}