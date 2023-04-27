package com.courses.server.service.impl;

import com.courses.server.dto.request.ExpertRequest;
import com.courses.server.entity.User;
import com.courses.server.repositories.ExpertRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.impl.ExpertServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ExpertServiceImplTest {

  @Mock
  private ExpertRepository expertRepository;

  @Mock
  private FileService fileService;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ExpertServiceImpl service;

  @Test
  public void create() {
    ExpertRequest req = new ExpertRequest();
    req.setStatus(Boolean.TRUE);
    req.setCompany("c");
    req.setDescription("d");
    req.setFullname("f");
    req.setJobTitle("j");
    req.setUserId(1L);

    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

    service.create(req);

    Mockito.verify(expertRepository).save(Mockito.any());
  }

  @Test
  public void update() {
    ExpertRequest req = new ExpertRequest();
    req.setStatus(Boolean.TRUE);
    req.setCompany("c");
    req.setDescription("d");
    req.setFullname("f");
    req.setJobTitle("j");
    req.setUserId(1L);

    Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

    service.create(req);

    Mockito.verify(expertRepository).save(Mockito.any());
  }

}
