package com.courses.server.services;

import com.courses.server.dto.request.InfoContactCourseRequest;
import com.courses.server.dto.request.NewClassRequest;
import com.courses.server.entity.InfoContactCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface InfoContactCourseService {
    void create(InfoContactCourseRequest req);

    void update(Long id, InfoContactCourseRequest req);

    void delete(Long id);

    Page<InfoContactCourse> getList(Pageable paging);

    InfoContactCourse getDetail(Long id);
}
