package com.courses.server.controller.admin;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.UpdateActiveUserDTO;
import com.courses.server.dto.response.UserResponse;
import com.courses.server.entity.User;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
public class AdminAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        List<UserResponse> listUserResponse = userService.getListUser();
        return ResponseEntity.ok(listUserResponse);
    }

    @PostMapping("/active")
    public ResponseEntity<?> updateActiveUser(@Validated @RequestBody UpdateActiveUserDTO activeUserDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        userService.updateActive(username, activeUserDTO);

        return ResponseEntity.ok(new MessageResponse("Update status user success"));
    }

    @PostMapping("/detail")
    public ResponseEntity<?> findUserByUsername(@Validated @RequestBody Map<String, String> username) {
        User user = userRepository.findByUsername(username.get("username"));
        return ResponseEntity.ok(new UserResponse(user));
    }
}
