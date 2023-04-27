package com.courses.server.services;

import com.courses.server.dto.request.SlideReq;
import com.courses.server.entity.Slide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface SlideService {
    void create(SlideReq req, MultipartFile image);

    void update(Long id, SlideReq req, MultipartFile image);

    Page<Slide> getAll(Integer status, Pageable pageable);

    List<Slide> getAllView();

    Slide getDetail(Long id);

    void delete(Long id);
}
