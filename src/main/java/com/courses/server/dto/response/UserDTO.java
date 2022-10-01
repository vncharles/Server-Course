package com.courses.server.dto.response;

import com.courses.server.entity.ERole;
import com.courses.server.entity.User;
import com.courses.server.utils.Utility;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String phoneNumber;
    private String avatar;
    private ERole role;
    private boolean active;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullname = user.getFullname();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().getName();
        this.active = user.isActive();
        this.avatar = "http://localhost:8080/api/account/downloadFile/" + user.getAvatar();
    }
}
