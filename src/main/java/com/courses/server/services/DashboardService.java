package com.courses.server.services;

import com.courses.server.dto.response.DashboardChartBar;
import com.courses.server.dto.response.DashboardAdminAndManager;
import com.courses.server.dto.response.DashboardDoughnut;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {
    
    DashboardAdminAndManager getOrders();
    DashboardAdminAndManager getTrainees();
    DashboardAdminAndManager getPosts();
    DashboardAdminAndManager getPackages();
    DashboardAdminAndManager getCombos();
    DashboardAdminAndManager getUsers();
    DashboardAdminAndManager getClasss();
    DashboardDoughnut getDoughnut();
    DashboardChartBar getChartBar(Integer categoty);
}
