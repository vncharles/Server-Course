package com.courses.server.services.impl;

import com.courses.server.dto.request.ManagerSubjectRequest;
import com.courses.server.dto.request.SubjectRequest;
import com.courses.server.entity.ERole;
import com.courses.server.entity.Subject;
import com.courses.server.entity.User;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.ForbiddenException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.SubjectRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private FileService fileService;

    @Override
    public List<Subject> getAllSubject(String username) {
        User user = userRepository.findByUsername(username);
        if(user.getRole().getName().equals(ERole.ROLE_MANAGER)) {
            return subjectRepository.findAllByManager_Id(user.getId());
        }
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectByCode(String username, Long id) {
        User user = userRepository.findByUsername(username);
        Subject subject = subjectRepository.findById(id).get();

        if(null==subject){
            throw new NotFoundException(404, "Subject is not found!!!");
        }

        if(user.getRole().getName().equals(ERole.ROLE_ADMIN)){
            return subject;
        }

        if(subject.getManager().getUsername().equals(username)){
            return subject;
        }

        throw new ForbiddenException(403, "Role forbidden!!");
    }

    @Override
    public void addSubject(SubjectRequest subjectRequest) {
//        System.out.println("Subject request service: " + subjectRequest);
        if(subjectRequest.getCode()==null || subjectRequest.getName()==null)
            throw new BadRequestException(400, "Please enter enough information!!");
        if(subjectRepository.findByCode(subjectRequest.getCode())!=null){
            throw new BadRequestException(400, "Code subject is exist");
        }
        Subject subject = new Subject();
        subject.setCode(subjectRequest.getCode());
        subject.setName(subjectRequest.getName());
        subject.setPrice(subjectRequest.getPrice());
        subject.setStatus(subjectRequest.isStatus());
        subject.setNote(subjectRequest.getNote());

        if(subjectRequest.getManager()!=null){
            User manager = userRepository.findByUsername(subjectRequest.getManager());
            if(null==manager){
                throw new NotFoundException(404, "Manager is not found!!!");
            } else if(manager.getRole().getName().equals(ERole.ROLE_MANAGER))
                subject.setManager(manager);
        }

        if(subjectRequest.getExpert()!=null){
            User expert = userRepository.findByUsername(subjectRequest.getExpert());
            if(null==expert){
                throw new NotFoundException(404, "Expert is not found!!!");
            } else if(expert.getRole().getName().equals(ERole.ROLE_EXPERT))
                subject.setExpert(expert);
        }


        subjectRepository.save(subject);
    }

    @Override
    public void updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(id).get();
        if(null==subject){
            throw new NotFoundException(404, "Subject is not found!!!");
        }

        if(subjectRequest.getCode()!=null)
            subject.setCode(subjectRequest.getCode());
        if(subjectRequest.getName()!=null)
            subject.setName(subjectRequest.getName());
        if(subjectRequest.getPrice()>0)
            subject.setPrice(subjectRequest.getPrice());
        if(subjectRequest.getNote()!=null)
            subject.setNote(subjectRequest.getNote());

        subject.setStatus(subjectRequest.isStatus());

        if(subjectRequest.getManager()!=null){
            User manager = userRepository.findByUsername(subjectRequest.getManager());
            if(null==manager){
                throw new NotFoundException(404, "Manager is not found!!!");
            }
            subject.setManager(manager);
        }
        if(subjectRequest.getExpert()!=null){
            User expert = userRepository.findByUsername(subjectRequest.getExpert());
            if(null==expert){
                throw new NotFoundException(404, "Expert is not found!!!");
            }
            subject.setExpert(expert);
        }
        subject.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        subjectRepository.save(subject);
    }

    @Override
    public void managerUpdateSubject(ManagerSubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(subjectRequest.getId()).get();
        if(null==subject){
            throw new NotFoundException(404, "Subject is not found!!!");
        }

        if(subjectRequest.isStatus()==true || subjectRequest.isStatus()==false)
            subject.setStatus(subjectRequest.isStatus());
        if(subjectRequest.getExpert()!=null){
            User expertNew = userRepository.findByUsername(subjectRequest.getExpert());
            if(null==expertNew){
                throw new NotFoundException(404, "Expert is not found!!!");
            }
            subject.setExpert(expertNew);
        }
        subject.setUpdatedDate(new Timestamp(new Date().getTime()).toString());

        subjectRepository.save(subject);
    }

    @Override
    public Subject searchByCode(String username, String code) {
        return null;
    }
}
