package com.courses.server.repositories;

import com.courses.server.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	List<Subject> findAllByManager_Id(Long id);

	Subject findByCode(String code);
	List<Subject> findAllByStatus(boolean status);
	//all paging
	Page<Subject> findAllByStatus(boolean status, Pageable pageable);

	@Query(value="select * from Subject where category_id = ?1", nativeQuery=true)
 	Page<Subject> findAllByCategory(long category, Pageable pageable);
	
	@Query(value = "select * from Subject where (code like %?1% or name like %?1%)", nativeQuery = true)
	Page<Subject> findAllByCodeOrName(String name, Pageable pageable);

	@Query(value = "select * from Subject where (code like %?1% or name like %?1%) and category_id = ?2", nativeQuery = true)
	Page<Subject> findAllByCodeOrNameAndCategory(String name, long category, Pageable pageable);

	@Query(value = "select * from Subject where (code like %?1% or name like %?1%) and status = ?2", nativeQuery = true)
	Page<Subject> findAllByCodeOrNameAndStatus(String name, boolean status, Pageable pageable);

	@Query(value = "select * from Subject where (code like %?1% or name like %?1%) and status = ?2 and category_id = ?3", nativeQuery = true)
	Page<Subject> findAllByStatusAndCodeOrNameAndCategory(String name, boolean status, long category,
			Pageable pageable);

	@Query(value = "select * from Subject where status = ?1 and category_id = ?2", nativeQuery = true)
	Page<Subject> findAllByStatusAndCategory(boolean status, long category, Pageable pageable);
	//manager paging
	@Query(value = "select * from Subject where manager_id = ?1", nativeQuery = true)
	Page<Subject> findAllByManager(long manager_id, Pageable pageable);
	
	@Query(value = "select * from Subject where status = ?1 and manager_id = ?2", nativeQuery = true)
	Page<Subject> findAllByManagerAndStatus(boolean status, long manager_id, Pageable pageable);

	@Query(value = "select * from Subject where category_id = ?1 and manager_id = ?2)", nativeQuery = true)
	Page<Subject> findAllByManagerAndCategory(long category, long manager_id, Pageable pageable);

	@Query(value = "select * from Subject where (code like %?1% or name like %?1%) and manager_id = ?2)", nativeQuery = true)
	Page<Subject> findAllByManagerAndCodeOrName(String name, long manager_id, Pageable pageable);

	@Query(value = "select * from Subject where (code like %?1% or name like %?1%) and category_id = ?2 and manager_id = ?3", nativeQuery = true)
	Page<Subject> findAllByManagerAndCodeOrNameAndCategory(String name, long category, long manager_id, Pageable pageable);

	@Query(value = "select * from Subject where (code like %?1% or name like %?1%) and status = ?2 and manager_id = ?3", nativeQuery = true)
	Page<Subject> findAllByManagerAndCodeOrNameAndStatus(String name, boolean status, long manager_id, Pageable pageable);

	@Query(value = "select * from Subject where (code like %?1% or name like %?1%) and status = ?2 and category_id = ?3 and manager_id = ?4", nativeQuery = true)
	Page<Subject> findAllByManagerAndStatusAndCodeOrNameAndCategory(String name, boolean status, long category, long manager_id,
			Pageable pageable);

	@Query(value = "select * from Subject where status = ?1 and category_id = ?2 and manager_id = ?3", nativeQuery = true)
	Page<Subject> findAllByManagerAndStatusAndCategory(boolean status, long category, long manager_id, Pageable pageable);
}
