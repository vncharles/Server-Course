package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.RoleRequest;
import com.courses.server.entity.Role;
import com.courses.server.repositories.RoleRepository;
import com.courses.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class AdminRoleController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/update")
    public ResponseEntity<?> updateRole(@Validated @RequestBody RoleRequest roleDTO) {
        userService.updateRole(roleDTO);
        return ResponseEntity.ok(new MessageResponse("Update role success"));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> listRole() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
}
