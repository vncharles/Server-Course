package com.courses.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Coupon extends BaseDomain{
    private String code;
    private int quantity;
    private int discountRate;
    private boolean status;
    private Date validFrom;
    private Date validTo;
}
