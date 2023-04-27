package com.courses.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Class extends BaseDomain{
	private String code;
	private String schedule;
	private String time;
	private Date dateFrom;
	private Date dateTo;
	private boolean status;

	@ManyToOne
	@JoinColumn(name = "package_id", referencedColumnName = "id")
	private Package aPackage;


	@ManyToOne
	@JoinColumn(name = "supporter_id", referencedColumnName = "id")
	private User supporter;

	@ManyToOne
	@JoinColumn(name = "branch_id", referencedColumnName = "setting_id")
	@JsonIgnore
	private Setting branch;

	@ManyToOne
	@JoinColumn(name = "trainer_id", referencedColumnName = "id")
	@JsonIgnore
	private Expert trainer;

}
