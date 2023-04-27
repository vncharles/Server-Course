package com.courses.server.dto.response;

import com.courses.server.entity.Expert;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpertDTO {
    private Long id;
    private String company;
    private String jobTitle;
    private boolean status;
    private String description;
    private UserDTO user;

    public ExpertDTO() {
    }

    public ExpertDTO(Expert expert) {
        this.id = expert.getId();
        this.company = expert.getCompany();
        this.jobTitle = expert.getJobTitle();
        this.status = expert.isStatus();
        this.description = expert.getDescription();
        if (expert.getUser() != null) {
            this.user = new UserDTO(expert.getUser());
        }
       
    }
}