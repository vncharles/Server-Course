package com.courses.server.dto.response;

import com.courses.server.entity.Combo;
import com.courses.server.entity.Feedback;
import lombok.Data;

@Data
public class FeedbackDTO {
    private Long id;
    private String body;
    private int vote;
    private ExpertDTO expert;
    private PackageDTO aPackage;
    private Combo combo;
    private UserDTO user;
    private String created_date;

    public FeedbackDTO(Feedback feedback) {
        id = feedback.getId();
        body = feedback.getBody();
        vote = feedback.getVote();
        if(feedback.getExpert()!=null) expert = new ExpertDTO(feedback.getExpert());
        if(feedback.getAPackage()!=null) aPackage = new PackageDTO(feedback.getAPackage());
        if(feedback.getCombo()!=null) combo = feedback.getCombo();
        user = new UserDTO(feedback.getUser());
        created_date= feedback.getCreatedDate();
    }
}
