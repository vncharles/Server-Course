package com.courses.server.controller;

import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.RegisterDTO;
import com.courses.server.dto.response.UserResponse;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.services.UserService;
import com.courses.server.services.impl.EmailSenderService;
import com.courses.server.utils.TemplateSendMail;
import com.courses.server.utils.Utility;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService senderService;

    @GetMapping("/users")
    @ApiOperation("Return all user in website")
    public ResponseEntity<?> getAllUser(){
        List<UserResponse> listUserResponse = userService.getListUser();
        return ResponseEntity.ok(listUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody Map<String, String> login) {
        String username = login.get("username");
        String password = login.get("password");
        return ResponseEntity.ok(userService.loginAccount(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterDTO registerDTO,
                                          HttpServletRequest request){

        String token = userService.createAccount(registerDTO);

        String registerLink = Utility.getSiteURL(request) + "/verify?token=" + token;

        String subject = "Here's the link to register";

        String content = TemplateSendMail.getContent(registerLink, "Confirm Account", "First, you need to confirm your account.");

        senderService.sendEmail(registerDTO.getEmail(), subject, content);

        return ResponseEntity.ok(new MessageResponse("Register success. Please check email verify!!!"));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@Validated @RequestParam("token") String token){
        User user = userService.getByRegisterToken(token);

        if (user == null) {
            throw new BadRequestException(1402, "token wrong");
        }

        userService.verifyRegister(user);
        return ResponseEntity.ok(new MessageResponse("Verify success"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> processForgotPassword(HttpServletRequest request,
                                                   @RequestBody Map<String, String> forgot){
        String token = RandomString.make(30);

        String email = forgot.get("email");

        userService.updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;

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

    @PutMapping("update_info")
    public ResponseEntity<?> updateInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ResponseEntity.ok(new MessageResponse("Sửa thành công"));
    }
}

