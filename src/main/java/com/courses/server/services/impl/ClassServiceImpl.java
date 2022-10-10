package com.courses.server.services.impl;

import com.courses.server.dto.request.ClassRequest;
import com.courses.server.entity.Class;
import com.courses.server.entity.User;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.ClassRepository;
import com.courses.server.repositories.UserRepository;
import com.courses.server.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addClass(ClassRequest classRequest) {
        Class _class = new Class();

        Random rand = new Random();
        String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        String code = "IS" + date +  String.format("%04d", rand.nextInt(10000));

//        System.out.println("Code: " + code);
        _class.setCode(code);
        _class.setPackages(classRequest.getPackages());
        _class.setDateFrom(classRequest.getDateFrom());
        _class.setDateTo(classRequest.getDateTo());

        if(classRequest.isStatus() || !classRequest.isStatus()) {
            _class.setStatus(classRequest.isStatus());
        } else _class.setStatus(false);

        if(classRequest.getTrainer() != null){
            User trainer = userRepository.findByUsername(classRequest.getTrainer());
            if(trainer==null){
                throw new NotFoundException(404, "Trainer is not found!!");
            }
            _class.setTrainer(trainer);
        }

        classRepository.save(_class);
    }

    @Override
    public void updateCLass(Long id, ClassRequest classRequest) {
        Class _class = classRepository.findById(id).get();
        if(_class == null) {
            throw new NotFoundException(404, "Class is not found!!");
        }

        if(classRequest.getPackages() != null) {
            _class.setPackages(classRequest.getPackages());
        }
        if(classRequest.getDateFrom() != null) {
            _class.setDateFrom(classRequest.getDateFrom());
        }
        if(classRequest.getDateTo() != null) {
            _class.setDateTo(classRequest.getDateTo());
        }

        if(classRequest.isStatus() || !classRequest.isStatus()) {
            _class.setStatus(classRequest.isStatus());
        }

        if(classRequest.getTrainer() != null){
            User trainer = userRepository.findByUsername(classRequest.getTrainer());
            if(trainer==null){
                throw new NotFoundException(404, "Trainer is not found!!");
            }
            _class.setTrainer(trainer);
        }

        classRepository.save(_class);
    }

    @Override
    public List<Class> getAllClass() {
        return classRepository.findAll();
    }

    @Override
    public Class getClassDetail(Long id) {
        Class _class = classRepository.findById(id).get();
        if(_class == null) {
            throw new NotFoundException(404, "Class is not found!!");
        }

        return _class;
    }
}
