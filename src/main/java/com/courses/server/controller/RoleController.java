package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.RoleDTO;
import com.courses.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateRole(@Validated @RequestBody RoleDTO roleDTO) {
        userService.setRole(roleDTO);
        return ResponseEntity.ok(new MessageResponse("Update role success"));
    }
}
