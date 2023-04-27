package com.courses.server.dto.response;

import lombok.Data;

@Data
public class DashboardSupporter {
    private long totalSoldOut;
    private PackageDTO aPackage;
}
