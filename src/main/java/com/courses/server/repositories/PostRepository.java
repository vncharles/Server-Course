package com.courses.server.repositories;

import com.courses.server.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    Collection<Post> findAllByOrderByCreationDateDesc();
	Page<Post> findAllByStatus(int status, Pageable pageable);

	@Query(value="select * from Post where category_id = ?1", nativeQuery=true)
 	Page<Post> findAllByCategory(long category, Pageable pageable);
	
	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%)", nativeQuery = true)
	Page<Post> findAllByTitleAndBrefInfo(String name, Pageable pageable);

	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%) and category_id = ?2", nativeQuery = true)
	Page<Post> findAllByTitleAndBrefInfoAndCategory(String name, long category, Pageable pageable);

	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%) and status = ?2", nativeQuery = true)
	Page<Post> findAllByTitleAndBrefInfoAndStatus(String name, int status, Pageable pageable);

	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%) and status = ?2 and category_id = ?3", nativeQuery = true)
	Page<Post> findAllByStatusAndTitleAndBrefInfoAndCategory(String name, int status, long category,
			Pageable pageable);

	@Query(value = "select * from Post where status = ?1 and category_id = ?2", nativeQuery = true)
	Page<Post> findAllByStatusAndCategory(int status, long category, Pageable pageable);
	//manager paging
	@Query(value = "select * from Post where user_id = ?1", nativeQuery = true)
	Page<Post> findAllByAuthor(long user_id, Pageable pageable);
	
	@Query(value = "select * from Post where status = ?1 and user_id = ?2", nativeQuery = true)
	Page<Post> findAllByAuthorAndStatus(int status, long user_id, Pageable pageable);

	@Query(value = "select * from Post where category_id = ?1 and user_id = ?2)", nativeQuery = true)
	Page<Post> findAllByAuthorAndCategory(long category, long user_id, Pageable pageable);

	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%) and user_id = ?2)", nativeQuery = true)
	Page<Post> findAllByAuthorAndTitleAndBrefInfo(String name, long user_id, Pageable pageable);

	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%) and category_id = ?2 and user_id = ?3", nativeQuery = true)
	Page<Post> findAllByAuthorAndTitleAndBrefInfoAndCategory(String name, long category, long user_id, Pageable pageable);

	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%) and status = ?2 and user_id = ?3", nativeQuery = true)
	Page<Post> findAllByAuthorAndTitleAndBrefInfoAndStatus(String name, int status, long user_id, Pageable pageable);

	@Query(value = "select * from Post where (title like %?1% or bref_info like %?1%) and status = ?2 and category_id = ?3 and user_id = ?4", nativeQuery = true)
	Page<Post> findAllByAuthorAndStatusAndTitleAndBrefInfoAndCategory(String name, int status, long category, long user_id,
			Pageable pageable);

	@Query(value = "select * from Post where status = ?1 and category_id = ?2 and user_id = ?3", nativeQuery = true)
	Page<Post> findAllByAuthorAndStatusAndCategory(int status, long category, long user_id, Pageable pageable);

	@Query(value = "SELECT * FROM post order by views desc LIMIT 0, ?1", nativeQuery = true)
	List<Post> findTopByOrderByViewsDesc(int top);

	@Query(value = "SELECT * FROM post order by created_date desc LIMIT 0, ?1", nativeQuery = true)
	List<Post> findTopRecent(int top);

	@Query(value = "SELECT count(*) FROM post WHERE created_date >= DATE(DATE_SUB(NOW(), INTERVAL (WEEKDAY(NOW()) + 7) DAY)) and created_date < DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
    long countOldPost();

    @Query(value = "SELECT count(*) FROM post WHERE created_date >= DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
    long countNewPost();
}
