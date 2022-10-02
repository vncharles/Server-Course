package com.courses.server.services;

import com.courses.server.dto.request.ManagerSubjectRequest;
import com.courses.server.dto.request.SubjectRequest;
import com.courses.server.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubject(String username);

    Subject getSubjectByCode(String username, Long id);

    void addSubject(SubjectRequest subjectRequest);

    void updateSubject(Long id, SubjectRequest subjectRequest);

    void managerUpdateSubject(ManagerSubjectRequest subjectRequest);

    Subject searchByCode(String username, String code);
}
