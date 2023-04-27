package com.courses.server.dto.response;

import lombok.Data;

@Data
public class DashboardAdminAndManager {
    private long total_new_user;
    private long total_old_user;
    private long total_old_class;
    private long total_new_class;
    private long total_old_package;
    private long total_new_package;
    private long total_old_combo;
    private long total_new_combo;
    private long total_trainee_off;
    private long total_trainee_onl;
    private long total_old_post;
    private long total_new_post;
    private long total_class_month;
    private long total_package_month;
    private long total_combo_month;
}
