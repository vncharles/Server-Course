package com.courses.server.dto.response;

import com.courses.server.entity.ERole;
import com.courses.server.entity.User;
import lombok.*;

@Getter @Setter
public class UserResponse{
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String phoneNumber;
    private String avatar;
    private ERole role;
    private boolean active;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullname = user.getFullname();
        this.phoneNumber = user.getPhoneNumber();
        this.avatar = user.getAvatar();
        this.role = user.getRole().getName();
        this.active = user.isActive();
    }
}
