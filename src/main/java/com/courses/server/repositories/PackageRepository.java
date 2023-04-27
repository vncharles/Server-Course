package com.courses.server.repositories;

import com.courses.server.entity.Package;
import com.courses.server.entity.Package;

import com.courses.server.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
	Page<Package> findAllByStatus(boolean status, Pageable pageable);

	@Query(value = "select * from Package where subject_id = ?1", nativeQuery = true)
	Page<Package> findAllBySubject(long category, Pageable pageable);

	@Query(value = "select * from Package where (title like %?1% or description like %?1%)", nativeQuery = true)
	Page<Package> findAllByTitleOrDescription(String keyword, Pageable pageable);

	@Query(value = "select * from Package where (title like %?1% or description like %?1%) and subject_id = ?2", nativeQuery = true)
	Page<Package> findAllByTitleOrDescriptionAndSubject(String keyword, long category, Pageable pageable);

	@Query(value = "select * from Package where (title like %?1% or description like %?1%) and status = ?2", nativeQuery = true)
	Page<Package> findAllByTitleOrDescriptionAndStatus(String keyword, boolean status, Pageable pageable);

	@Query(value = "select * from Package where (title like %?1% or description like %?1%) and status = ?2 and subject_id = ?3", nativeQuery = true)
	Page<Package> findAllByTitleOrDescriptionAndStatusAndSubject(String keyword, boolean status, long category,
			Pageable pageable);

	@Query(value = "select * from Package where status = ?1 and subject_id = ?2", nativeQuery = true)
	Page<Package> findAllByStatusAndSubject(boolean status, long category, Pageable pageable);


	@Query(value = "SELECT * FROM package where status = 1 order by views desc LIMIT 0, ?1", nativeQuery = true)
	List<Package> findTopByOrderByViewsDesc(int top);

	@Query(value = "SELECT * FROM package p\n" +
			"WHERE p.id = (SELECT op.package_id FROM order_package op\n" +
			"WHERE op.order_id IN (SELECT o.id FROM order o WHERE o.status=3)\n" +
			"GROUP BY op.package_id\n" +
			"ORDER BY COUNT(op.package_id) DESC LIMIT 1)", nativeQuery = true)
	Package top1PackageBuy();
}
