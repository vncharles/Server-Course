package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.RoleRequest;
import com.courses.server.entity.Setting;
import com.courses.server.repositories.SettingRepository;
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
    private SettingRepository settingRepository ;

    @PostMapping("/update")
    public ResponseEntity<?> updateRole(@Validated @RequestBody RoleRequest roleDTO) {
        userService.updateRole(roleDTO);
        return ResponseEntity.ok(new MessageResponse("Cập nhật role thành công"));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Setting>> listRole() {
        List<Setting> roles = settingRepository.findByType(1);
        return ResponseEntity.ok(roles);
    }
}
