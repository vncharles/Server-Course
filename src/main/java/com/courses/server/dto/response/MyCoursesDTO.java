package com.courses.server.dto.response;

import com.courses.server.entity.UserPackage;
import lombok.Data;
import java.util.Date;

@Data
public class MyCoursesDTO {
    private UserDTO user;
    private PackageDTO aPackage;
    private OrderDTO order;
    private Date validFrom;
    private Date validTo;

    public MyCoursesDTO(UserPackage up) {
        user = new UserDTO(up.getUser());
        aPackage = new PackageDTO(up.getAPackage());
        order = new OrderDTO(up.getOrder());
        validFrom = up.getValidFrom();
        validTo = up.getValidTo();
    }
}
