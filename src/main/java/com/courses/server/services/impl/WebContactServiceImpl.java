package com.courses.server.services.impl;

import com.courses.server.dto.request.WebContactRequest;
import com.courses.server.entity.WebContact;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.repositories.UserRepository;
import com.courses.server.repositories.WebContactRepository;
import com.courses.server.services.WebContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WebContactServiceImpl implements WebContactService {
    @Autowired
    private WebContactRepository webContactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addWebContact(WebContactRequest webContactRequest) {
        WebContact webContact = new WebContact(
                webContactRequest.getFullName(),
                webContactRequest.getEmail(),
                webContactRequest.getPhoneNumber(),
                webContactRequest.getMessage(),
                false
        );

        webContactRepository.save(webContact);
    }

    @Override
    public List<WebContact> getWebContactList(String username) {
        if(!userRepository.existsByUsername(username)){
            throw new BadRequestException(1302, "account has not login");
        }

        List<WebContact> webContacts = webContactRepository.findAll();
        return webContacts;
    }

    @Override
    public void updateWebContact(String username, Long id, WebContactRequest webContactRequest) {
        if(!userRepository.existsByUsername(username)){
            throw new BadRequestException(1302, "account has not login");
        }

        Optional<WebContact> webContactFind = webContactRepository.findById(id);
        WebContact webContact = webContactFind.get();

        webContact.setFullName(webContactRequest.getFullName());
        webContact.setEmail(webContactRequest.getEmail());
        webContact.setPhoneNumber(webContactRequest.getPhoneNumber());
        webContact.setMessage(webContactRequest.getMessage());
        webContact.setUpdatedDate(new Timestamp(new Date().getTime()).toString());
        webContactRepository.save(webContact);
    }

    @Override
    public void updateStatusWebContact(String username, Long id, boolean status) {
        if(!userRepository.existsByUsername(username)){
            throw new BadRequestException(1302, "account has not login");
        }

        Optional<WebContact> webContactFind = webContactRepository.findById(id);
        WebContact webContact = webContactFind.get();

        webContact.setStatus(!status);
        webContact.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        webContactRepository.save(webContact);
    }

    @Override
    public void deleteWebContact(String username, Long id) {
        if(!userRepository.existsByUsername(username)){
            throw new BadRequestException(1302, "account has not login");
        }

        webContactRepository.deleteById(id);
    }
}
