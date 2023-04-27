package com.courses.server.services.impl;

import com.courses.server.dto.request.SlideReq;
import com.courses.server.entity.Slide;
import com.courses.server.exceptions.BadRequestException;
import com.courses.server.exceptions.NotFoundException;
import com.courses.server.repositories.SlideRepository;
import com.courses.server.services.FileService;
import com.courses.server.services.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlideServiceImpl implements SlideService {
    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private FileService fileService;

    @Override
    public void create(SlideReq slideReq, MultipartFile image) {
        if(image.isEmpty()) {
            throw new BadRequestException(400, "You can upload image!");
        }
        if(slideReq.getValidTo()==null) {
            throw new BadRequestException(400, "You can input valid to!");
        }
        String fileName = fileService.storeFile(image);
        Slide slide = new Slide(fileName, slideReq.getValidTo(), slideReq.getStatus(), slideReq.getUrl(), slideReq.getTitle());
        slideRepository.save(slide);
    }

    @Override
    public void update(Long id, SlideReq slideReq, MultipartFile image) {
        Slide slide = slideRepository.findById(id).get();
        if(slide==null) {
            throw new NotFoundException(404, "Slide  Không tồn tại!");
        }
        if(image!=null) {
            String fileName =  fileService.storeFile(image);
            slide.setImageUrl(fileName);
        }
        if(slideReq.getValidTo()!=null) {
            slide.setValidTo(slideReq.getValidTo());
        }
        if(slideReq.getStatus()>=0 && slideReq.getStatus()<=5) {
            slide.setStatus(slideReq.getStatus());
        }
        if(slideReq.getTitle()!=null) {
            slide.setTitle(slideReq.getTitle());
        }
        if(slideReq.getUrl()!=null) {
            slide.setUrl(slideReq.getUrl());
        }
        slideRepository.save(slide);
    }

    @Override
    public Page<Slide> getAll(Integer status, Pageable pageable) {
        Page<Slide> pageSlide;
        if(status!=null) {
            pageSlide = slideRepository.findAllByStatus(status, pageable);
        } else {
            pageSlide = slideRepository.findAll(pageable);
        }
        return pageSlide;
    }

    @Override
    public List<Slide> getAllView() {
        List<Slide> slideList = slideRepository.findAll();
        List<Slide> response = slideList.stream().filter(slide -> {
            if (slide.getValidTo().compareTo(Calendar.getInstance().getTime())<0){
                return false;
            }
            if(slide.getStatus()!=1) return false;

            return true;
        }).collect(Collectors.toList());
        return response;
    }

    @Override
    public Slide getDetail(Long id) {
        Slide slide = slideRepository.findById(id).get();
        if(slide==null) {
            throw new NotFoundException(404, "Slide  Không tồn tại!");
        }
        return slide;
    }

    @Override
    public void delete(Long id) {
        Slide slide = slideRepository.findById(id).get();
        if(slide==null) {
            throw new NotFoundException(404, "Slide  Không tồn tại!");
        }
        slideRepository.delete(slide);
    }
}
