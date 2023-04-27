package com.courses.server.service.impl;

import com.courses.server.dto.response.JwtResponse;
import com.courses.server.entity.User;
import com.courses.server.repositories.UserRepository;
import com.courses.server.security.jwt.JwtUtils;
import com.courses.server.security.services.UserDetailsImpl;
import com.courses.server.services.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl service;

  @Mock
  private UserRepository userRepository;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtUtils jwtUtils;

  //another mockbean here if needed

  @Test
  public void loginAccount_shouldWork() {
    Mockito.when(userRepository.existsByEmail(Mockito.any())).thenReturn(true);
    Authentication authentication = new UsernamePasswordAuthenticationToken(
            new UserDetailsImpl(1L, "", "Admin@gmail.com", "Admin1234@", true, Collections.emptyList()), null);
    Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
    Mockito.when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt");

    JwtResponse jwtResponse = service.loginAccount("Admin@gmail.com", "Admin1234@");

    Assertions.assertThat(jwtResponse.getAccessToken()).isEqualTo("jwt");
  }

  @Test
  public void updatePassword_shouldWork() {
    User customer = new User();
    service.updatePassword(customer, "1234Admin@");

    Assertions.assertThat(new BCryptPasswordEncoder().matches("1234Admin@", customer.getPassword())).isTrue();
  }

}
