package com.courses.server.repositories;

import com.courses.server.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User findByResetPasswordToken(String token);

    User findByRegisterToken(String token);

    Page<User> findAllByActive(boolean status, Pageable pageable);
 
	@Query(value="select * from User where role_id = ?1", nativeQuery=true)
    Page<User> findAllByRole(long role, Pageable pageable);

    @Query(value="select * from User where role_id = ?1 and active = 1", nativeQuery=true)
    List<User> findAllByRole(long role);

	@Query(value="select * from User where fullname like %?1% or email like %?1%", nativeQuery=true)
    Page<User> findAllByFullnameOrEmail(String name, Pageable pageable);

	@Query(value="select * from User where (fullname like %?1% or email like %?1%) and role_id = ?2", nativeQuery=true)
    Page<User> findAllByFullnameOrEmailAndRole(String name, long role, Pageable pageable);
    
	@Query(value="select * from User where (fullname like %?1% or email like %?1%) and active = ?2", nativeQuery=true)
    Page<User> findAllByFullnameOrEmailAndActive(String name, boolean status, Pageable pageable);
    
	@Query(value="select * from User where (fullname like %?1% or email like %?1%) and active  = ?2 and role_id = ?3", nativeQuery=true)
    Page<User> findAllByActiveAndFullnameOrEmailAndRole(String name, boolean status, long role, Pageable pageable);

	@Query(value="select * from User where active = ?1 and role_id = ?2", nativeQuery=true)
    Page<User> findAllByActiveAndRole(boolean status, long role, Pageable pageable);

    @Query(value="SELECT COUNT(*) FROM user where created_date >= DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery=true)
    long count_new_user();

    @Query(value="SELECT COUNT(*) FROM user where created_date >= DATE(DATE_SUB(NOW(), INTERVAL (WEEKDAY(NOW()) + 7) DAY)) and created_date < DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery=true)
    long count_old_user();
}
