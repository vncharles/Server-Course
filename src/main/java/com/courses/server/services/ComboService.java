package com.courses.server.services;

import com.courses.server.dto.request.ComboRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Combo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ComboService {
    void create(ComboRequest req, MultipartFile image);

    void update(Long id, ComboRequest req, MultipartFile image);

    void delete(Long id);

    Page<Combo> getList(Pageable paging, Params params) throws IOException;

    Combo getDetail(Long id);
}
