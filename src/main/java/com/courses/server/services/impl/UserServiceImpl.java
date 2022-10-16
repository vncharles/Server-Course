package com.courses.server.services.impl;

import com.courses.server.dto.request.UpdateActiveUserRequest;
import com.courses.server.dto.request.UserUpdateRequest;
import com.courses.server.dto.request.RegisterRequest;
import com.courses.server.dto.request.RoleRequest;
import com.courses.server.dto.response.JwtResponse;
import com.courses.server.dto.response.UserDTO;
import com.courses.server.entity.ERole;
import com.courses.server.entity.Role;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.ForbiddenException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.RoleRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.security.jwt.JwtUtils;
import com.courses.server.security.services.UserDetailsImpl;
import com.courses.server.services.FileService;
import com.courses.server.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
//    @Value("${avatar-file-upload-dir}")
//    String path;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String createAccount(RegisterRequest registerDTO) {
        if(registerDTO.getEmail()==null){
            throw new BadRequestException(1200, "Email is required");
        }
        if(registerDTO.getUsername()==null){
            throw new BadRequestException(1000, "Username is required");
        }
        if(registerDTO.getPassword()==null){
            throw new BadRequestException(1100, "Password is required");
        }

        User userCheckMail = userRepository.findByEmail(registerDTO.getEmail());
        if(userCheckMail != null && userCheckMail.isActive()) {
            throw new BadRequestException(1201, "Email has already existed");
        } else if(!registerDTO.getEmail().toLowerCase().matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
            throw new BadRequestException(1203, "Email invalid");
        }

        User userCheckUsername = userRepository.findByUsername(registerDTO.getUsername());
        if(userCheckUsername != null && userCheckUsername.isActive()){
            throw new BadRequestException(1001, "Username has already existed");
        } else if(!registerDTO.getUsername().matches("^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")){
            throw new BadRequestException(1003, "Username invalid, username include letters and digits, at least 6, case-insensitive");
        }

        if(!registerDTO.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,20}$")){
            throw new BadRequestException(1101, "Password invalid, Password must be at least 8 characters with at least 1 special character 1 uppercase letter 1 lowercase letter and 1 number!");
        }

        User user;

        if(!userRepository.existsByEmail(registerDTO.getEmail())) {
            user = new User(registerDTO.getEmail(), registerDTO.getUsername(), encoder.encode(registerDTO.getPassword()), false);
        }  else {
            user = userRepository.findByEmail(registerDTO.getEmail());
            user.setUsername(registerDTO.getUsername());
            user.setPassword(encoder.encode(registerDTO.getPassword()));
        }

        // Nếu user bth không có set role thì set thành ROLE_USER
        Role userRole = roleRepository.findByName(ERole.ROLE_GUEST)
                .orElseThrow(() -> new NotFoundException(404, "Error: Role is not found"));

        user.setRole(userRole);

        String token = RandomString.make(30);

        user.setRegisterToken(token);
        user.setTimeRegisterToken(LocalDateTime.now());

        userRepository.save(user);

        return user.getRegisterToken();
    }

    @Override
    public void verifyRegister(User user) {
        user.setActive(true);
        user.setRegisterToken(null);
        userRepository.save(user);
    }

    @Override
    public JwtResponse loginAccount(String email, String password) {
        if(!userRepository.existsByEmail(email)){
            throw new NotFoundException(1002, "username has not existed");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
        } catch (Exception ex){
            throw new BadRequestException(1102, "wrong password");
        }
    }

    public void updateResetPasswordToken(String token, String email) throws NotFoundException {
        User customer = userRepository.findByEmail(email);
        if (customer != null) {
            customer.setResetPasswordToken(token);
            userRepository.save(customer);
        } else {
            throw new BadRequestException(1202, "email has not existed");
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User customer, String newPassword) {
        if(newPassword==null || newPassword==""){
            throw new BadRequestException(1100, "password is required");
        } else if(!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{8,20}$")){
            throw new BadRequestException(1101, "password invalid, Password must be at least 8 characters with at least 1 special character 1 uppercase letter 1 lowercase letter and 1 number!");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);

        customer.setResetPasswordToken(null);
        customer.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        userRepository.save(customer);
    }

    @Override
    public User getByRegisterToken(String token) {
        return userRepository.findByRegisterToken(token);
    }

    @Override
    public List<User> getListUser() throws IOException {
        return userRepository.findAll();
    }

    @Override
    public void updateRole(RoleRequest roleDTO) {
        String username = roleDTO.getUsername();
        ERole roles = roleDTO.getRole();

        if(!userRepository.existsByUsername(username)){
            throw new NotFoundException(1002, "username has not existed");
        }

        User user = userRepository.findByUsername(username);
        System.out.println("ROLE: " + roleDTO.getRole());
        Role userRole = roleRepository.findByName(roleDTO.getRole())
                .orElseThrow(() -> new NotFoundException(404, "Error: Role is not found"));

        user.setRole(userRole);

        user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, String username, UserUpdateRequest updateDTO) {
        if(!userRepository.existsById(id)) {
            throw new BadRequestException(1302, "User has not found");
        }

        User user = userRepository.findById(id).get();

        if(username!=null){
            if(user.getId() != userRepository.findByUsername(username).getId()){
                throw new ForbiddenException(403, "Error forbidden!");
            }
        }

        if(updateDTO.getUsername()!=null){
            if(userRepository.existsByUsername(updateDTO.getUsername())){
                throw new BadRequestException(1001, "username has already existed");
            }
            user.setUsername(updateDTO.getUsername());
        }
        if(updateDTO.getPhoneNumber()!=null)
            user.setPhoneNumber(updateDTO.getPhoneNumber());
        if(updateDTO.getFullname()!=null)
            user.setFullname(updateDTO.getFullname());
        if(updateDTO.getPassword()!=null)
            user.setPassword(encoder.encode(updateDTO.getPassword()));

        user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        userRepository.save(user);
    }

    @Override
    public User getUserDetail(String username) {
        if(!userRepository.existsByUsername(username)){
            throw new BadRequestException(1302, "account has not login");
        }

        return userRepository.findByUsername(username);
    }

    @Override
    public void updateAvatar(String username, MultipartFile file) throws IOException {
        User user = userRepository.findByUsername(username);
        String fileName = fileService.storeFile(file);
        user.setAvatar(fileName);
        user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        userRepository.save(user);
    }

    @Override
    public void updateActive(String username, UpdateActiveUserRequest activeUserDTO) {
        if(!userRepository.existsByUsername(username)){
            throw new BadRequestException(1302, "account has not login");
        }
        if(!userRepository.existsByUsername(activeUserDTO.getUsername())){
            throw new NotFoundException(1002, "username has not existed");
        }

        User user = userRepository.findByUsername(activeUserDTO.getUsername());
        user.setActive(!activeUserDTO.isStatus());
        user.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        userRepository.save(user);
    }


}
