package com.courses.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingRequest {
	private long setting_id;
	
	private int type_id;
	
	private String setting_title;

	private String setting_value;

	private String display_order;

	private Boolean status;

	private String desciption;
}
