package com.courses.server.repositories;

import com.courses.server.dto.response.DashboardChartBarDto;
import com.courses.server.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

        @Query(value = "select * from `order` where user_id = ?1 and (status = 1 or status = 2) order by created_date desc", nativeQuery = true)
        Page<Order> getMyOrderProcess(Long userId, Pageable pageable);

        @Query(value = "select * from `order` where user_id = ?1 and status = 4 order by created_date desc", nativeQuery = true)
        Page<Order> getMyOrderCancel(Long userId, Pageable pageable);

        @Query(value = "select * from `order` where user_id = ?1 and status = 3 order by created_date desc", nativeQuery = true)
        Page<Order> getMyOrder(Long userId, Pageable pageable);

        @Query(value = "select * from `order` where user_id = ?1 and status = 0", nativeQuery = true)
        Optional<Order> getMyCart(Long userId);

        @Query(value = "SELECT MONTH(o.created_date) as month, YEAR(o.created_date) as year, o.total_cost-o.total_discount as total FROM `order` o\n"
                        +
                        "GROUP BY MONTH(o.created_date), YEAR(o.created_date)", nativeQuery = true)
        List<Object> getAllTurnover();

        @Query(value = "select * from `order` where class_id is not null and (status = 1 or status = 2) order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPay(Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is not null and (o.status = 1 or o.status = 2) and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeyword(String keyword, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is not null and o.status = ?2 and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeywordAndStatus(String keyword, int status, Pageable pageable);

        @Query(value = "select * from `order` where class_id is not null and status = ?1 order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayByStatus(int status, Pageable pageable);

        @Query(value = "select * from `order` where class_id = ?1 and (status = 1 or status = 2) order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayByClass(Long class_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id = ?2 and (o.status = 1 or o.status = 2) and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeywordByClass(String keyword, Long class_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id = ?3 and o.status = ?2 and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeywordAndStatusByClass(String keyword, int status, Long class_id,
                        Pageable pageable);

        @Query(value = "select * from `order` where class_id = ?2 and status = ?1 order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayByStatusByClass(int status, Long class_id, Pageable pageable);

        @Query(value = "select * from `order` where class_id is null and (status = 1 or status = 2) order by created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPay(Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is null and (o.status = 1 or o.status = 2) and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPayKeyword(String keyword, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is null and o.status = ?2 and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPayKeywordAndStatus(String keyword, int status, Pageable pageable);

        @Query(value = "select * from `order` where class_id is null and status = ?1 order by created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPayByStatus(int status, Pageable pageable);

        @Query(value = "select * from `order` where (status = 3 or status = 4) order by created_date desc", nativeQuery = true)
        Page<Order> getListOrdered(Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where (o.status = 3 or o.status = 4) and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOrderedKeyword(String keyword, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.status = ?2 and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOrderedKeywordAndStatus(String keyword, int status, Pageable pageable);

        @Query(value = "select * from `order` where status = ?1 order by created_date desc", nativeQuery = true)
        Page<Order> getListOrderedByStatus(int status, Pageable pageable);

        @Query(value = "select * from `order` where coupon_id = ?1 and user_id = ?2", nativeQuery = true)
        List<Order> getCountCouponUserOrder(long coupon_id, long user_id);

        @Query(value = "select * from `order` where coupon_id = ?1 and customer_id = ?2", nativeQuery = true)
        List<Order> getCountCouponCustomerOrder(long coupon_id, long customer_id);

        @Query(value = "select * from `order` where class_id is not null and (status = 1 or status = 2) and (supporter_id = ?1 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayRoleSupporter(Long supporter_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is not null and (o.status = 1 or o.status = 2) and  (o.supporter_id = ?2 or o.supporter_id is null) and "
                        +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeywordRoleSupporter(String keyword, Long supporter_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is not null and o.status = ?2 and  (o.supporter_id = ?3 or o.supporter_id is null) and "
                        +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeywordAndStatusRoleSupporter(String keyword, int status, Long supporter_id,
                        Pageable pageable);

        @Query(value = "select * from `order` where class_id is not null and status = ?1 and (supporter_id = ?2 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayByStatusRoleSupporter(int status, Long supporter_id, Pageable pageable);

        @Query(value = "select * from `order` where class_id = ?2 and (status = 1 or status = 2) and (supporter_id = ?1 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayRoleSupporterByClass(Long supporter_id, Long class_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id = ?3 and (o.status = 1 or o.status = 2) and  (o.supporter_id = ?2 or o.supporter_id is null) and "
                        +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeywordRoleSupporterByClass(String keyword, Long supporter_id, Long class_id,
                        Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id = ?4 and o.status = ?2 and  (o.supporter_id = ?3 or o.supporter_id is null) and "
                        +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayKeywordAndStatusRoleSupporterByClass(String keyword, int status,
                        Long supporter_id, Long class_id, Pageable pageable);

        @Query(value = "select * from `order` where class_id = ?3 and status = ?1 and (supporter_id = ?2 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOfflineNotPayByStatusRoleSupporterByClass(int status, Long supporter_id, Long class_id,
                        Pageable pageable);

        @Query(value = "select * from `order` where class_id is null and (status = 1 or status = 2) and (supporter_id = ?1 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPayRoleSupporter(Long supporter_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is null and (o.status = 1 or o.status = 2) and  (o.supporter_id = ?2 or o.supporter_id is null) and "
                        +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPayKeywordRoleSupporter(String keyword, Long supporter_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.class_id is null and o.status = ?2 and (o.supporter_id = ?3 or o.supporter_id is null) and "
                        +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPayKeywordAndStatusRoleSupporter(String keyword, int status, Long supporter_id,
                        Pageable pageable);

        @Query(value = "select * from `order` where class_id is null and status = ?1 and (supporter_id = ?2 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOnlineNotPayByStatusRoleSupporter(int status, Long supporter_id, Pageable pageable);

        @Query(value = "select * from `order` where (status = 3 or status = 4) and (supporter_id = ?1 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOrderedRoleSupporter(Long supporter_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where (o.status = 3 or o.status = 4) and (o.supporter_id = ?2 or o.supporter_id is null) and "
                        +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOrderedKeywordRoleSupporter(String keyword, Long supporter_id, Pageable pageable);

        @Query(value = "select o.* from `order` as o " +
                        "left join`user` as u on o.user_id = u.id " +
                        "left join`customer` as c on o.customer_id = c.id " +
                        "where o.status = ?2 and (o.supporter_id = ?3 or o.supporter_id is null) and " +
                        "(u.fullname like %?1% or u.username like %?1% or c.full_name like %?1% or c.email like %?1% or u.email like %?1%) "
                        +
                        "order by o.created_date desc", nativeQuery = true)
        Page<Order> getListOrderedKeywordAndStatusRoleSupporter(String keyword, int status, Long supporter_id,
                        Pageable pageable);

        @Query(value = "select * from `order` where status = ?1 and (supporter_id = ?2 or supporter_id is null) order by created_date desc", nativeQuery = true)
        Page<Order> getListOrderedByStatusRoleSupporter(int status, Long supporter_id, Pageable pageable);

        @Query(value = "SELECT count(*) FROM `order` WHERE class_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL (WEEKDAY(NOW()) + 7) DAY)) and created_date < DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
        long countOldClass();

        @Query(value = "SELECT count(*) FROM `order` WHERE class_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
        long countNewClass();

        @Query(value = "SELECT count(*) FROM `order` WHERE class_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL DAY(NOW()) DAY))", nativeQuery = true)
        long countMonthClass();

        @Query(value = "SELECT week(created_date) as 'label', SUM(total_cost) as 'value' FROM `order` where Month(created_date) = Month(NOW()) group by week(created_date), Month(created_date)", nativeQuery = true)
        List<DashboardChartBarDto> getListSalesWeek();

        @Query(value = "SELECT week(created_date) as 'label', SUM(total_cost) as 'value' FROM `order` where Month(created_date) = Month(NOW()) group by week(created_date), Month(created_date), status having status = 3", nativeQuery = true)
        List<DashboardChartBarDto> getListRevenueWeek();

        @Query(value = "SELECT Month(created_date) as 'label', SUM(total_cost) as 'value' FROM `order` where Year(created_date) = Year(NOW()) group by Month(created_date),Year(created_date)", nativeQuery = true)
        List<DashboardChartBarDto> getListSalesMonth();

        @Query(value = "SELECT Month(created_date) as 'label', SUM(total_cost) as 'value' FROM `order` where Year(created_date) = Year(NOW()) group by Month(created_date),Year(created_date), status having status = 3", nativeQuery = true)
        List<DashboardChartBarDto> getListRevenueMonth();

        @Query(value = "SELECT Year(created_date) as 'label', SUM(total_cost) as 'value' FROM `order` group by year(created_date)", nativeQuery = true)
        List<DashboardChartBarDto> getListSalesYear();

        @Query(value = "SELECT Year(created_date) as 'label', SUM(total_cost) as 'value' FROM `order` group by year(created_date), status having status = 3", nativeQuery = true)
        List<DashboardChartBarDto> getListRevenueYear();
}