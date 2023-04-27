package com.courses.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    private String fullName;
    private String email;
    private String mobile;
    private String codeCoupon;
    private List<Long> packages;
    private List<Long> combos;
    private Long classId;
}