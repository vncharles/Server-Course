package com.courses.server.dto.response;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenDTO {
	
	private Boolean get_all_data;
	
	private Boolean can_delete;
	
	private Boolean can_add;
	
	private Boolean can_edit;
}
