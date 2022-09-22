package com.courses.server.services;

import com.courses.server.dto.request.RegisterDTO;
import com.courses.server.dto.request.RoleDTO;
import com.courses.server.dto.response.JwtResponse;
import com.courses.server.dto.response.UserResponse;
import com.courses.server.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<UserResponse> getListUser();

    public String createAccount(RegisterDTO registerDTO);

    public void verifyRegister(User user);

    public JwtResponse loginAccount(String username, String password);

    public void updateResetPasswordToken(String token, String email);

    public User getByResetPasswordToken(String token);

    public void updatePassword(User customer, String newPassword);

    public User getByRegisterToken(String token);

    public void setRole(RoleDTO roleDTO);


}
