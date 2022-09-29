package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.RegisterRequest;
import com.courses.server.dto.request.UserUpdateRequest;
import com.courses.server.dto.response.UserDTO;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.services.UserService;
import com.courses.server.services.impl.EmailSenderService;
import com.courses.server.utils.TemplateSendMail;
import com.courses.server.utils.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Value("${avatar-file-upload-dir}")
    String path;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService senderService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody Map<String, String> login) {
        String username = login.get("username");
        String password = login.get("password");
        return ResponseEntity.ok(userService.loginAccount(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequest registerDTO,
                                          HttpServletRequest request){

        String token = userService.createAccount(registerDTO);

        String registerLink = Utility.getSiteURL(request) + "/api/account/verify?token=" + token;

        String subject = "Here's the link to register";

        String content = TemplateSendMail.getContent(registerLink, "Confirm Account", "First, you need to confirm your account.");

        senderService.sendEmail(registerDTO.getEmail(), subject, content);

        return ResponseEntity.ok(new MessageResponse("Register success. Please check email verify!!!"));
    }

    @GetMapping("/verify")
    public String verifyUser(HttpServletRequest request){
        String token = request.getParameter("token");
        User user = userService.getByRegisterToken(token);

        if(user.isActive()){
//            throw new BadRequestException(1300, "account has already active");
            return TemplateSendMail.getError("Account has already active!!!", "http://localhost:3000/react", "FCourses");
        }
        if(Duration.between(user.getTimeRegisterToken(), LocalDateTime.now()).toMinutes()>5){
//            throw new BadRequestException(1400, "code time out");
            return TemplateSendMail.getError("Authentication timeout. Please register again!", "http://localhost:3000/react/register", "Register");
        }
        if (user == null) {
            return TemplateSendMail.getError("Token wrong!!!", "http://localhost:3000/react", "FCourses");
        }

        userService.verifyRegister(user);
        return TemplateSendMail.getSuccess("http://localhost:3000/react/login");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> processForgotPassword(HttpServletRequest request,
                                                   @RequestBody Map<String, String> forgot){
        String token = RandomString.make(30);

        String email = forgot.get("email");

        userService.updateResetPasswordToken(token, email);
        String resetPasswordLink = "http://localhost:3000/react/reset-password/" + token;

        String subject = "Here's the link to reset your password";

        String content = TemplateSendMail.getContent(resetPasswordLink, "Reset Password", "You are asking for a password reset.");

        senderService.sendEmail(email, subject, content);

        return ResponseEntity.ok(new MessageResponse("Fotgot password success. Please check email!!!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(HttpServletRequest request,
                                                  @RequestParam("token") String token,
                                                  @RequestBody Map<String, String> forgot) {

        User user = userService.getByResetPasswordToken(token);
        String password = forgot.get("password");

        if (user == null) {
            throw new BadRequestException(1402, "token wrong");
        }

        userService.updatePassword(user, password);

        return ResponseEntity.ok(new MessageResponse("Reset password success"));
    }

    @PutMapping("/update-info")
    public ResponseEntity<?> updateInfo(@RequestBody UserUpdateRequest updateDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        userService.updateUser(username, updateDTO);
        return ResponseEntity.ok(new MessageResponse("Update info success"));
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        userService.updateAvatar(username, avatar);
        return ResponseEntity.ok(new MessageResponse("Upload avatar success"));
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> userDetail() throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.getUserDetail(username);
        UserDTO userResponse = new UserDTO(user);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path+user.getAvatar());
        System.out.println("Unput stream: " + inputStream);
        System.out.println("Path: " + path+user.getAvatar());
        userResponse.setAvatar(StreamUtils.copyToByteArray(inputStream));

        return ResponseEntity.ok(userResponse);
    }
}

