package com.courses.server.dto.response;

import com.courses.server.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String phoneNumber;
    private String avatar;
    private String role;
    private String note;
    private boolean active;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail() != null ? user.getEmail() : "";
        this.fullname = user.getFullname() != null ? user.getFullname() : "";
        this.phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : "";
        this.role = user.getRole().getSetting_value();
        this.active = user.isActive();
        this.avatar = user.getAvatar();
    }

    public UserDTO(User user, Boolean flag) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail() != null ? user.getEmail() : "";
        this.fullname = user.getFullname() != null ? user.getFullname() : "";
        this.phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : "";
        this.role = user.getRole().getSetting_value();
        this.active = user.isActive();
        this.note = user.getNote();
        this.avatar = user.getAvatar();
    }
}
