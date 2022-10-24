package com.courses.server.services.impl;

import com.courses.server.entity.ERole;
import com.courses.server.entity.Role;
import com.courses.server.repositories.RoleRepository;
import com.courses.server.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role checkRole() {
        Role role = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority rolee: auth.getAuthorities()){
            role = roleRepository.findByName(ERole.valueOf(rolee.getAuthority())).get();
        }
        return role;
    }
}
