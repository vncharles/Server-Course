package com.courses.server.controller;

import com.courses.server.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getOrders() {
        return ResponseEntity.ok(dashboardService.getOrders());
    }

    @GetMapping("/trainee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getTrainees() {
        return ResponseEntity.ok(dashboardService.getTrainees());
    }

    @GetMapping("/post")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getPosts() {
        return ResponseEntity.ok(dashboardService.getPosts());
    }

    @GetMapping("/package")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getPackages() {
        return ResponseEntity.ok(dashboardService.getPackages());
    }

    @GetMapping("/combo")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getCombos() {
        return ResponseEntity.ok(dashboardService.getCombos());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(dashboardService.getUsers());
    }

    @GetMapping("/class")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getClasss() {
        return ResponseEntity.ok(dashboardService.getClasss());
    }

    @GetMapping("/doughnut")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getDoughnut() {
        return ResponseEntity.ok(dashboardService.getDoughnut());
    }

    @GetMapping("/chart-bar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPPORTER', 'ROLE_MARKETER')")
    public ResponseEntity<?> getChartBar(@RequestParam(defaultValue = "0") int category) {
        return ResponseEntity.ok(dashboardService.getChartBar(category));
    }
}
