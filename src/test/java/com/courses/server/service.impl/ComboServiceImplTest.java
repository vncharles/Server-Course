package com.courses.server.service.impl;

import com.courses.server.dto.request.ComboPackageRequest;
import com.courses.server.dto.request.ComboRequest;
import com.courses.server.entity.Combo;
import com.courses.server.entity.ComboPackage;
import com.courses.server.entity.Package;
import com.courses.server.repositories.ComboPackageRepository;
import com.courses.server.repositories.ComboRepository;
import com.courses.server.repositories.PackageRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.impl.ComboServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ComboServiceImplTest  {

    @Mock
    private ComboRepository comboRepository;

    @Mock
    private PackageRepository packageRepository;
    
    @Mock
	  private FileService fileService;

    @Mock
    private ComboPackageRepository comboPackageRepository;

    @InjectMocks
    private ComboServiceImpl service;

    @Test
    public void create() {
        ComboRequest req = new ComboRequest();
        req.setTitle("t");
        req.setDescription("d");
        req.setPackages(List.of(new ComboPackageRequest() {{setPackageId(1L);}}));

        Mockito.when(fileService.storeFile(null)).thenReturn("abc");
        Mockito.when(packageRepository.findById(Mockito.any())).thenReturn(Optional.of(new Package()));

        service.create(req, null);

        Mockito.verify(comboRepository).save(Mockito.any());
    }

    @Test
    public void update() {
        ComboRequest req = new ComboRequest();
        req.setTitle("t");
        req.setDescription("d");
        req.setPackages(List.of(new ComboPackageRequest() {{setPackageId(1L);setSalePrice(1D);}}));

        Mockito.when(fileService.storeFile(Mockito.any())).thenReturn("abc");
        Mockito.when(comboRepository.findById(Mockito.any())).thenReturn(Optional.of(new Combo() {{setComboPackages(Set.of(new ComboPackage() {{setId(1L);set_package(new Package() {{setId(1L);}});}}));}}));

        service.update(1L, req, Mockito.mock(MultipartFile.class));
    }

}