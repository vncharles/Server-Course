package com.courses.server.service.impl;

import com.courses.server.dto.request.PackageReq;
import com.courses.server.entity.Package;
import com.courses.server.entity.Subject;
import com.courses.server.repositories.PackageRepository;
import com.courses.server.repositories.SubjectRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.impl.PackageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class PackageServiceImplTest {

  @Mock
  private PackageRepository packageRepository;

  @Mock
  private SubjectRepository subjectRepository;

  @Mock
  private FileService fileService;

  @InjectMocks
  private PackageServiceImpl service;

  @Test
  public void create_shouldWork() {
    PackageReq req = new PackageReq();
    req.setSubjectId(1L);
    req.setTitle("t");
    req.setExcerpt("e");
    req.setDuration(1);
    req.setStatus(Boolean.TRUE);
    req.setListPrice(2D);
    req.setSalePrice(0D);

    Mockito.when(subjectRepository.findById(1L)).thenReturn(Optional.of(new Subject()));

    service.create(req, null);

    Mockito.verify(packageRepository).save(Mockito.any());
  }

  @Test
  public void update_shouldWork() {
    PackageReq req = new PackageReq();
    req.setSubjectId(1L);
    req.setTitle("t");
    req.setExcerpt("e");
    req.setDuration(1);
    req.setStatus(Boolean.TRUE);
    req.setListPrice(2D);
    req.setSalePrice(0D);

    Mockito.when(packageRepository.findById(1L)).thenReturn(Optional.of(new Package()));
    Mockito.when(subjectRepository.findById(1L)).thenReturn(Optional.of(new Subject()));

    service.create(req, null);

    Mockito.verify(packageRepository).save(Mockito.any());
  }

}
