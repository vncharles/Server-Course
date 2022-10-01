package com.courses.server.dto.response;

import com.courses.server.entity.Subject;
import lombok.Data;

@Data
public class SubjectDTO {
    private Long id;
    private String code;
    private String name;
    private boolean status;
    private String note;
    private UserDTO manager;
    private UserDTO expert;
    private String image;

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.code = subject.getCode();
        this.name = subject.getName();
        this.status = subject.isStatus();
        this.note = subject.getNote();
        if(subject.getManager()!=null)
            this.manager = new UserDTO(subject.getManager());
        if(subject.getExpert()!=null)
            this.expert = new UserDTO(subject.getExpert());
        if(subject.getImage()!=null)
            this.image = "http://localhost:8080/api/account/downloadFile/" + subject.getImage();
    }
}
