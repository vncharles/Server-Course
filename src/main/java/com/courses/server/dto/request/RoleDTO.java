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
    private Set<ERole> roles;

    public RoleDTO(String username, Set<ERole> roles) {
        this.username = username;
        this.roles = roles;
    }

    public RoleDTO() {
    }
}
