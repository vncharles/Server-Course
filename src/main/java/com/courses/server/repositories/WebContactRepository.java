package com.courses.server.repositories;

import com.courses.server.entity.WebContact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WebContactRepository  extends JpaRepository<WebContact, Long> {
	
	 	Page<WebContact> findAllByStatus(boolean status, Pageable pageable);
	
		@Query(value="select * from web_contact where category_id = ?1", nativeQuery=true)
	 	Page<WebContact> findAllByCategory(long category, Pageable pageable);

		@Query(value="select * from web_contact where full_Name like %?1% or email like %?1%", nativeQuery=true)
	    Page<WebContact> findAllByFullNameOrEmail(String name, Pageable pageable);
	    
		@Query(value="select * from web_contact where (full_Name like %?1% or email like %?1%) and category_id = ?2", nativeQuery=true)
		  Page<WebContact> findAllByFullNameOrEmailAndCategory(String name, long category, Pageable pageable);
	   
	    @Query(value="select * from web_contact where (full_Name like %?1% or email like %?1%) and status = ?2", nativeQuery=true)	   
	    Page<WebContact> findAllByFullNameOrEmailAndStatus(String name, boolean status, Pageable pageable);

		@Query(value="select * from web_contact where (full_Name like %?1% or email like %?1%) and status = ?2 and category_id = ?3", nativeQuery=true)
	    Page<WebContact> findAllByStatusAndFullNameOrEmailAndCategory(String name, boolean status, long category, Pageable pageable);

		@Query(value="select * from web_contact where status = ?1 and category_id = ?2", nativeQuery=true)
	    Page<WebContact> findAllByStatusAndCategory(boolean status, long category, Pageable pageable);

		@Query(value="select * from web_contact where supporter_id = ?1 or supporter_id is null", nativeQuery=true)
		Page<WebContact> findAllRoleSupporter(long supporter_id, Pageable pageable);
	
		@Query(value="select * from web_contact where status = ?1 and (supporter_id = ?2 or supporter_id is null)", nativeQuery=true)
		Page<WebContact> findAllByStatusRoleSupporter(boolean status, long supporter_id, Pageable pageable);
	
		@Query(value="select * from web_contact where category_id = ?1 and (supporter_id = ?2 or supporter_id is null)", nativeQuery=true)
	 	Page<WebContact> findAllByCategoryRoleSupporter(long category, long supporter_id, Pageable pageable);

		@Query(value="select * from web_contact where (full_Name like %?1% or email like %?1%) and (supporter_id = ?2 or supporter_id is null)", nativeQuery=true)
	    Page<WebContact> findAllByFullNameOrEmailRoleSupporter(String name, long supporter_id, Pageable pageable);
	    
		@Query(value="select * from web_contact where (full_Name like %?1% or email like %?1%) and category_id = ?2 and (supporter_id = ?3 or supporter_id is null)", nativeQuery=true)
		  Page<WebContact> findAllByFullNameOrEmailAndCategoryRoleSupporter(String name, long category, long supporter_id, Pageable pageable);
	   
	    @Query(value="select * from web_contact where (full_Name like %?1% or email like %?1%) and status = ?2 and (supporter_id = ?3 or supporter_id is null)", nativeQuery=true)	   
	    Page<WebContact> findAllByFullNameOrEmailAndStatusRoleSupporter(String name, boolean status, long supporter_id, Pageable pageable);

		@Query(value="select * from web_contact where (full_Name like %?1% or email like %?1%) and status = ?2 and category_id = ?3 and (supporter_id = ?4 or supporter_id is null)", nativeQuery=true)
	    Page<WebContact> findAllByStatusAndFullNameOrEmailAndCategoryRoleSupporter(String name, boolean status, long category, long supporter_id, Pageable pageable);

		@Query(value="select * from web_contact where status = ?1 and category_id = ?2 and (supporter_id = ?3 or supporter_id is null)", nativeQuery=true)
	    Page<WebContact> findAllByStatusAndCategoryRoleSupporter(boolean status, long category, long supporter_id, Pageable pageable);
}
