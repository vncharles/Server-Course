package com.courses.server.services;

import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.TraineeRequest;
import com.courses.server.entity.Trainee;
import com.courses.server.entity.UserPackage;

import io.jsonwebtoken.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TraineeService {
    void create(TraineeRequest req, HttpServletRequest request);

    void update(Long id, TraineeRequest req);

    void delete(Long id);

    Page<Trainee> getList(Pageable paging, Params params) throws IOException;

    Page<UserPackage> getListOnline(Pageable paging, Params params) throws IOException;

    Trainee getDetail(Long id);

    Page<Trainee> getListTraineDetail(Pageable pageable);
}
