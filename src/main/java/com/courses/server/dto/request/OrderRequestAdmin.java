package com.courses.server.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestAdmin {
    private int status;
    private String fullName;
    private String email;
    private String mobile;
    private String codeCoupon;
    private String note;
    private List<Long> packages;
    private List<Long> combos;
    private Long classId;
}