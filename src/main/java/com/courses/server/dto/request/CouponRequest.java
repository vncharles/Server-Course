package com.courses.server.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class CouponRequest {
    private Integer quantity;
    private Integer discountRate;
    private Boolean status;
    private Date validFrom;
    private Date validTo;
}
