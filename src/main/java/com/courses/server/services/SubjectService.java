package com.courses.server.services;

import com.courses.server.dto.request.ManagerSubjectRequest;
import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.SubjectRequest;
import com.courses.server.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface SubjectService {
    Page<Subject> getAllSubject(Pageable paging, Params params) throws IOException;

    List<Subject> getAllActive();

    Subject getSubjectByCode(String username, Long id);

    void addSubject(SubjectRequest subjectRequest);

    void updateSubject(Long id, SubjectRequest subjectRequest);

    void managerUpdateSubject(ManagerSubjectRequest subjectRequest);

    Subject searchByCode(String username, String code);
}
