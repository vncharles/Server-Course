package com.courses.server.services;

import com.courses.server.dto.request.UpdateActiveUserRequest;
import com.courses.server.dto.request.UserUpdateRequest;
import com.courses.server.dto.request.RegisterRequest;
import com.courses.server.dto.request.RoleRequest;
import com.courses.server.dto.response.JwtResponse;
import com.courses.server.dto.response.UserDTO;
import com.courses.server.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    List<User> getListUser() throws IOException;

    String createAccount(RegisterRequest registerDTO);

    void verifyRegister(User user);

    JwtResponse loginAccount(String username, String password);

    void updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(User customer, String newPassword);

    User getByRegisterToken(String token);

    void updateRole(RoleRequest roleDTO);

    void updateUser(String username, UserUpdateRequest user);

    User getUserDetail(String username);

    void updateAvatar(String username, MultipartFile image) throws IOException;

    void updateActive(String username, UpdateActiveUserRequest activeUserDTO);

}
