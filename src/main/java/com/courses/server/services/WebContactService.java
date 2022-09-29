package com.courses.server.services;

import com.courses.server.dto.request.WebContactRequest;
import com.courses.server.entity.WebContact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WebContactService {
    void addWebContact(WebContactRequest webContactRequest);

    List<WebContact> getWebContactList(String username);

    void updateWebContact(String username, Long id, WebContactRequest webContactRequest);

    void updateStatusWebContact(String username, Long id, boolean status);

    void deleteWebContact(String username, Long id);
}
