package com.courses.server.dto.request;

import com.courses.server.entity.ERole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RoleDTO {
    private String username;
    private ERole role;

    public RoleDTO(String username, ERole role) {
        this.username = username;
        this.role = role;
    }

    public RoleDTO() {
    }
}
