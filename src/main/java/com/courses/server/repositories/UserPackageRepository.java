package com.courses.server.repositories;

import com.courses.server.entity.User;
import com.courses.server.entity.UserPackage;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
        Page<UserPackage> findAllByUser(User user, Pageable pageable);

        Page<UserPackage> findAll(Pageable pageable);

        @Transactional
        @Modifying
        @Query(value = "update user_package set status = 0 where date_to < DATE(NOW())", nativeQuery = true)
        void checkEndPage();

        // UserPackage findByAPackageAndUser(Package aPackage, User user);
        @Query(value = "SELECT up.* FROM user_package as up " +
                        "join user as u on up.user_id = u.id " +
                        "where u.fullname like %?1% or u.username like %?1% or u.email like %?1%", nativeQuery = true)
        Page<UserPackage> getListUserPackageKeyword(String keyword, Pageable pageable);

        @Query(value = "SELECT up.* FROM user_package as up " +
                        "join user as u on up.user_id = u.id " +
                        "where (u.fullname like %?1% or u.username like %?1% or u.email like %?1%) and up.status = ?2", nativeQuery = true)
        Page<UserPackage> getListUserPackageKeywordAndStatus(String keyword, boolean status, Pageable pageable);

        @Query(value = "select * from user_package where status = ?1", nativeQuery = true)
        Page<UserPackage> getListUserPackageByStatus(boolean status, Pageable pageable);

        @Query(value = "SELECT up.* FROM user_package as up " +
                        "join user as u on up.user_id = u.id " +
                        "where (u.fullname like %?1% or u.username like %?1% or u.email like %?1%) and package_id = ?2", nativeQuery = true)
        Page<UserPackage> getListUserPackageKeywordAndPackage(String keyword, Long package_id, Pageable pageable);

        @Query(value = "SELECT up.* FROM user_package as up " +
                        "join user as u on up.user_id = u.id " +
                        "where (u.fullname like %?1% or u.username like %?1% or u.email like %?1%) and up.status = ?2  and package_id = ?3", nativeQuery = true)
        Page<UserPackage> getListUserPackageKeywordAndStatusAndPackage(String keyword, boolean status, Long package_id,
                        Pageable pageable);

        @Query(value = "select * from user_package where status = ?1  and package_id = ?2", nativeQuery = true)
        Page<UserPackage> getListUserPackageByStatusAndPackage(boolean status, Long package_id, Pageable pageable);

        @Query(value = "select * from user_package where package_id = ?1", nativeQuery = true)
        Page<UserPackage> getListUserPackageByPackage(Long package_id, Pageable pageable);

        @Query(value = "SELECT COUNT(*) FROM user_package where status = 1", nativeQuery = true)
        long count_active_trainee_onl();
}
