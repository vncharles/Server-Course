package com.courses.server.repositories;

import com.courses.server.entity.Coupon;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    @Query(value = "select * from coupon where curdate() >= valid_from and curdate() <= valid_to and code = ?1 and status = 1", nativeQuery = true)
    Optional<Coupon> findByCodeAccess(String code);

}
