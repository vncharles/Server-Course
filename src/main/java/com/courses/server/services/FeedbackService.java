package com.courses.server.services;

import com.courses.server.dto.request.FeedbackRequest;
import com.courses.server.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {
    void create(FeedbackRequest req);
    
    void createAdmin(FeedbackRequest req);

    void update(Long id, FeedbackRequest req);

    void delete(Long id);

    Page<Feedback> getList(int status, long info, Pageable pageable);

    Feedback getDetail(Long id);
}
