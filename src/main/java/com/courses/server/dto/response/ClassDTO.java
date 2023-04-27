package com.courses.server.dto.response;

import com.courses.server.entity.Class;
import com.courses.server.entity.Setting;
import lombok.Data;

import java.util.Date;

@Data
public class ClassDTO {
    private Long id;
    private String code;
    private Date dateFrom;
    private Date dateTo;
    private boolean status;
    private Setting branch;
    private String schedule;
    private String time;
    private PackageDTO packages;
    private ExpertDTO trainer;
	private UserDTO supporter;

    public ClassDTO(Class _class) {
        this.id = _class.getId();
        this.packages = _class.getAPackage()!= null ? new PackageDTO(_class.getAPackage()) : null;
        this.code = _class.getCode();
        this.dateFrom = _class.getDateFrom();
        this.dateTo = _class.getDateTo();
        this.branch = _class.getBranch();
        this.schedule = _class.getSchedule();
        this.time = _class.getTime();
        this.status = _class.isStatus();
        this.trainer = _class.getTrainer()!= null ? new ExpertDTO(_class.getTrainer()) : null;
		this.supporter = new UserDTO(_class.getSupporter());
    }
}
