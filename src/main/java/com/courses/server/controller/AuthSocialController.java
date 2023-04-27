package com.courses.server.controller;

import com.courses.server.config.GooglePojo;
import com.courses.server.dto.response.JwtResponse;
import com.courses.server.entity.ETypeAccount;
import com.courses.server.entity.Setting;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.repositories.SettingRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.security.jwt.JwtUtils;
import com.courses.server.security.services.UserDetailsImpl;
import com.courses.server.utils.GoogleUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/oauth2")
public class AuthSocialController {
    @Autowired
    private GoogleUtils googleUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping(value = "/google")
    public JwtResponse loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            throw new BadRequestException(1402, "Sai mã token");
        }

        String[] chunks = code.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));


        ObjectMapper mapper = new ObjectMapper();
        String token = googleUtils.getToken(code);
        JsonNode node = mapper.readTree(token);
        String accessToken = node.get("access_token").textValue();

        GooglePojo googlePojo = null;
        if (accessToken != null) {
            googlePojo = googleUtils.getUserInfo(accessToken);
        }
        UserDetails userDetail = googleUtils.buildUser(googlePojo);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(userDetail.getUsername());

        if (user == null) {
            user = new User();
            user.setEmail(googlePojo.getEmail());
            user.setUsername(googlePojo.getEmail());
            user.setPassword(encoder.encode(googlePojo.getSocial_user_id()));
            user.setType_account(ETypeAccount.GOOGLE);

            Setting userRole = settingRepository.findByValueAndType("ROLE_GUEST", 1)
                    .orElseThrow(() -> new RuntimeException("Error: Role  Không tồn tại"));
            user.setRole(userRole);

            userRepository.save(user);
        }
        Authentication authen = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), googlePojo.getSocial_user_id()));
        String jwt = jwtUtils.generateJwtToken(authen);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
