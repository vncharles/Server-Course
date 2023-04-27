package com.courses.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {
	private long role_id;
	private long screen_id;
	private boolean get_all_data;
	private boolean can_edit;
	private boolean can_delete;
	private boolean can_add;
}
