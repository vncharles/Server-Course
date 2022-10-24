package com.courses.server.services;

import com.courses.server.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role checkRole();
}
