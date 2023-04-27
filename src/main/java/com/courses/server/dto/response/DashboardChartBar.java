package com.courses.server.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class DashboardChartBar {
    private List<String> list_label;
    private List<Double> list_revenue;
    private List<Double> list_sales;
}
