package com.courses.server.services;

import com.courses.server.dto.request.ClassRequest;
import com.courses.server.entity.Class;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassService {
    void addClass(ClassRequest classRequest);

    void updateCLass(Long id, ClassRequest classRequest);

    List<Class> getAllClass();

    Class getClassDetail(Long id);

}
