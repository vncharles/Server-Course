package com.courses.server.entity;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

@Table(name = "Permissions")
public class Permissions  {
	/**
	 * 
	 */
	@EmbeddedId
    private PermissionId permissionId;
	
	@NotNull 
	private Boolean get_all_data;
	
	@NotNull
	private Boolean can_delete;
	
	@NotNull
	private Boolean can_add;
	
	@NotNull
	private Boolean can_edit;
}
