package com.courses.server.repositories;

import com.courses.server.entity.Setting;
import com.courses.server.entity.Type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

	@Query(value = "select * from Setting where setting_value = ?1 and type_id =?2", nativeQuery = true)
	Optional<Setting> findByValueAndType(String value, int type);

	@Query(value = "select * from Setting where type_id =?1", nativeQuery = true)
	List<Setting> findByType(int type);

	@Query(value = "select * from Setting where setting_id =?1", nativeQuery = true)
	Optional<Setting> findById(long setting_id);

	@Query(value = "select * from Setting where type_id =?1", nativeQuery = true)
	List<Setting> findAllByType(Long id);

	Page<Setting> findAllByStatus(boolean status, Pageable pageable);

	@Query(value="select * from Setting where type_id = ?1", nativeQuery=true)
	Page<Setting> findAllByType(int type, Pageable pageable);

	@Query(value="select * from Setting where setting_title like %?1% or setting_value like %?1%", nativeQuery=true)
	Page<Setting> findAllBySetting_titleContaining(String setting_title, Pageable pageable);
	
	@Query(value="select * from Setting where (setting_title like %?1% or setting_value like %?1%) and type_id = ?2", nativeQuery=true)
	Page<Setting> findAllBySetting_titleContainingAndType(String setting_title, int type, Pageable pageable);

	@Query(value="select * from Setting where (setting_title like %?1% or setting_value like %?1%) and status = ?2", nativeQuery=true)
	Page<Setting> findAllBySetting_titleContainingAndStatus(String setting_title, boolean status, Pageable pageable);

	@Query(value="select * from Setting where (setting_title like %?1% or setting_value like %?1%) and status = ?2 and type_id = ?3", nativeQuery=true)
	Page<Setting> findAllByStatusAndSetting_titleContainingAndType(String setting_title, boolean status,  int type, Pageable pageable);
	
	@Query(value="select * from Setting where status = ?1 and type_id = ?2", nativeQuery=true)
	Page<Setting> findAllByStatusAndType(boolean status, int type, Pageable pageable);
}
