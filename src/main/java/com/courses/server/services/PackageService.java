package com.courses.server.services;

import com.courses.server.dto.request.PackageReq;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PackageService {
    void create(PackageReq req, MultipartFile image);

    void update(Long id, PackageReq req, MultipartFile image);

    void delete(Long id);

    Page<Package> getList(Pageable paging, Params params) throws IOException;

    List<Package> getTopView(int top);

    Package getDetail(Long id);
}
