package com.courses.server.dto.response;

import lombok.Data;

@Data
public class DashboardDoughnut {
    private long total_submit;
    private long total_verfi;
    private long total_done;
    private long total_cancel;
}
