package com.courses.server.dto.response;

import com.courses.server.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserResponse{
    private Long id;
    private String username;
    private String email;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
