package com.courses.server.utils;

import com.courses.server.exceptions.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authen {
    public static void check(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(username == null || username.equals("anonymousUser")){
            throw new BadRequestException(1302, "User has not login");
        }
    }
}
