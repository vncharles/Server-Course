package com.courses.server.entity;
import java.io.Serializable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

public class PermissionId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne()
    @JoinColumn(name = "role_id", referencedColumnName = "setting_id")
    private Setting role_id;
	@ManyToOne()
    @JoinColumn(name = "screen_id", referencedColumnName = "setting_id")
    private Setting screen_id;
}
