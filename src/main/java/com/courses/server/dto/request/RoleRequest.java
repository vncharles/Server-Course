package com.courses.server.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RoleRequest {
    private String username;
    private String role;

    public RoleRequest(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public RoleRequest() {
    }
}
