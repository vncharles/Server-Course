package com.courses.server.controller;
import com.courses.server.dto.MessageResponse;
import com.courses.server.dto.request.RegisterRequest;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.services.UserService;
import com.courses.server.services.impl.EmailSenderService;
import com.courses.server.utils.TemplateSendMail;
import com.courses.server.utils.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailSenderService senderService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody Map<String, String> login) {
        String email = login.get("email");
        String password = login.get("password");
        return ResponseEntity.ok(userService.loginAccount(email, password));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequest registerDTO,
                                          HttpServletRequest request){

        String token = userService.createAccount(registerDTO);

        String registerLink = Utility.getSiteURL(request) + "/api/account/verify?token=" + token;

        String subject = "Here's the link to register";

        String content = TemplateSendMail.getContent(registerLink, "Confirm Account", "First, you need to confirm your account.");

        senderService.sendEmail(registerDTO.getEmail(), subject, content);

        return ResponseEntity.ok(new MessageResponse("Đăng ký thành công, vui lòng kiểm ra email để xem thông tin xác thực!!!"));
    }

    @GetMapping("/verify")
    public String verifyUser(HttpServletRequest request){
        String token = request.getParameter("token");
        User user = userService.getByRegisterToken(token);

        if(user.isActive()){
            return TemplateSendMail.getError("Account has already active!!!", "https://lms.nextin.com.vn", "LRS Education");
        }
        if(Duration.between(user.getTimeRegisterToken(), LocalDateTime.now()).toMinutes()>10){
            return TemplateSendMail.getError("Authentication timeout. Please register again!", "https://lms.nextin.com.vn/register", "Register");
        }
        if (user == null) {
            return TemplateSendMail.getError("Token wrong!!!", "https://lms.nextin.com.vn", "LRS Education");
        }

        userService.verifyRegister(user);
        return TemplateSendMail.getSuccess("https://lms.nextin.com.vn/login");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> processForgotPassword(HttpServletRequest request,
                                                   @RequestBody Map<String, String> forgot){
        String token = RandomString.make(30);

        String email = forgot.get("email");

        userService.updateResetPasswordToken(token, email);
        String resetPasswordLink = "https://lms.nextin.com.vn/reset-password/" + token;

        String subject = "Here's the link to reset your password";

        String content = TemplateSendMail.getContent(resetPasswordLink, "Reset Password", "You are asking for a password reset.");

        senderService.sendEmail(email, subject, content);

        return ResponseEntity.ok(new MessageResponse("Gửi yêu cầu quên mật khẩu thành công, vui lòng check email của bạn!!!"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(HttpServletRequest request,
                                                  @RequestParam("token") String token,
                                                  @RequestBody Map<String, String> forgot) {

        System.out.println(token);
        User user = userService.getByResetPasswordToken(token);
        String password = forgot.get("password");
        System.out.println(password);
        if (user == null) {
            throw new BadRequestException(1402, "Sai mã token");
        }
        userService.updatePassword(user, password);

        return ResponseEntity.ok(new MessageResponse("Đặt lại mật khẩu thành công"));
    }

}

