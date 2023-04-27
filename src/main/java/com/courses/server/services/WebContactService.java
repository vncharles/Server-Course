package com.courses.server.services;

import com.courses.server.dto.request.Params;
import com.courses.server.dto.request.WebContactRequest;
import com.courses.server.entity.WebContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface WebContactService {
    void addWebContact(WebContactRequest webContactRequest);

    Page<WebContact> getWebContactList(Pageable paging, Params params) throws IOException;

    void updateWebContact(String username, Long id, WebContactRequest webContactRequest);

    WebContact getClassDetail(Long id);

    void updateStatusWebContact(String username, Long id, boolean status);

    void deleteWebContact(String username, Long id);
}
