package com.courses.server.services;

import com.courses.server.dto.request.ClassRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ClassService {
    void addClass(ClassRequest classRequest);

    void updateCLass(Long id, ClassRequest classRequest);

    Page<Class> getAllClass(Pageable paging, Params params, boolean isGuest) throws IOException;

    Class getClassDetail(Long id);

}
