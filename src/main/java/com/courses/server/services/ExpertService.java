package com.courses.server.services;

import com.courses.server.dto.request.ExpertRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.PostReq;
import com.courses.server.entity.Expert;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ExpertService {
    Expert getById(Long id);

    Page<Expert> getAllManage(Pageable pageable, Params params) throws IOException;

    Page<Expert> getAllView(Pageable pageable);

    void create(ExpertRequest expertRequest);

    void update(Long id, ExpertRequest expertRequest, MultipartFile image);

    void delete(Long id);
}
