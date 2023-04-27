package com.courses.server.repositories;

import com.courses.server.entity.Class;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
	Page<Class> findAllByStatus(boolean status, Pageable pageable);

	@Query(value = "select * from Class where (code like %?1% or packages like %?1%)", nativeQuery = true)
	Page<Class> findAllByCodeOrName(String name, Pageable pageable);
	 
	@Query(value = "select * from Class where (code like %?1% or packages like %?1%) and status = ?2", nativeQuery = true)
	Page<Class> findAllByCodeOrNameAndStatus(String name, boolean status, Pageable pageable);

	@Query(value = "select * from Class where trainer_id = ?1", nativeQuery = true)
	Page<Class> findAllByTrainer(long trainer_id, Pageable pageable);

	@Query(value = "select * from Class where (code like %?1% or packages like %?1%) and trainer_id = ?2)", nativeQuery = true)
	Page<Class> findAllByTrainerAndCodeOrName(String name, long trainer_id, Pageable pageable);
	
	@Query(value = "select * from Class where (code like %?1% or packages like %?1%) and status = ?2 and trainer_id = ?3", nativeQuery = true)
	Page<Class> findAllByTrainerAndCodeOrNameAndStatus(String name, boolean status, long trainer_id, Pageable pageable);

	@Query(value = "select * from Class where status = ?1 and trainer_id = ?2 ", nativeQuery = true)
	Page<Class> findAllByTrainerAndStatus(boolean status, long trainer, Pageable pageable);

	@Query(value = "select * from Class where supporter_id = ?1 or supporter_id is null", nativeQuery = true)
	Page<Class> findAllRoleSupporter(Long supporter_id, Pageable pageable);

	@Query(value = "select * from Class where (code like %?1% or packages like %?1%) and (supporter_id = ?2 or supporter_id is null)", nativeQuery = true)
	Page<Class> findAllByCodeOrNameRoleSupporter(String name, Long supporter_id, Pageable pageable);
	
	@Query(value = "select * from Class where (code like %?1% or packages like %?1%) and status = ?2 and (supporter_id = ?3 or supporter_id is null)", nativeQuery = true)
	Page<Class> findAllByCodeOrNameAndStatusRoleSupporter(String name, boolean status, Long supporter_id, Pageable pageable);

	@Query(value = "select * from Class where trainer_id = ?1 and (supporter_id = ?2 or supporter_id is null)", nativeQuery = true)
	Page<Class> findAllByTrainerRoleSupporter(long trainer_id, Long supporter_id, Pageable pageable);

	@Query(value = "select * from Class where status = ?1 and (supporter_id = ?2 or supporter_id is null)", nativeQuery = true)
	Page<Class> findAllByStatusRoleSupporter(boolean status, Long supporter_id, Pageable pageable);

	@Query(value = "select * from Class where (code like %?1% or packages like %?1%) and trainer_id = ?2 and (supporter_id = ?3 or supporter_id is null)", nativeQuery = true)
	Page<Class> findAllByTrainerAndCodeOrNameRoleSupporter(String name, long trainer_id, Long supporter_id, Pageable pageable);
	
	@Query(value = "select * from Class where (code like %?1% or packages like %?1%) and status = ?2 and trainer_id = ?3 and (supporter_id = ?4 or supporter_id is null)", nativeQuery = true)
	Page<Class> findAllByTrainerAndCodeOrNameAndStatusRoleSupporter(String name, boolean status, long trainer_id, Long supporter_id, Pageable pageable);

	@Query(value = "select * from Class where status = ?1 and trainer_id = ?2 and (supporter_id = ?3 or supporter_id is null)", nativeQuery = true)
	Page<Class> findAllByTrainerAndStatusRoleSupporter(boolean status, long trainer, Long supporter_id, Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "update Class set status = 0 where DATE(date_from) <= DATE(NOW())", nativeQuery = true)
	void checkStartPage();

	@Query(value = "SELECT COUNT(*) FROM Class where NOW() >= date_from and NOW() <= date_to", nativeQuery = true)
	long count_active_class();
}
