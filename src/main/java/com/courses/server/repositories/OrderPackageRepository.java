package com.courses.server.repositories;

import com.courses.server.entity.Order;
import com.courses.server.entity.OrderPackage;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OrderPackageRepository extends JpaRepository<OrderPackage, Long> {
    Page<OrderPackage> findAllByOrder(Order order, Pageable pageable);

    List<OrderPackage> findAllByOrder(Order order);

    @Query(value = "SELECT * FROM order_package where activation_key = ?1", nativeQuery = true) // if want to write
                                                                                                // nativequery then mask
                                                                                                // nativeQuery as true
    OrderPackage findByActivationKey(String code);

    @Modifying
    @Query(value = "SELECT op.* FROM `order` as o join order_package as op on o.id = op.order_id where o.customer_id = ?2 and o.class_id = ?1", nativeQuery = true) // if
                                                                                                                                                                    // want
                                                                                                                                                                    // to
                                                                                                                                                                    // write
                                                                                                                                                                    // nativequery
                                                                                                                                                                    // then
                                                                                                                                                                    // mask
                                                                                                                                                                    // nativeQuery
                                                                                                                                                                    // as
                                                                                                                                                                    // true
    List<OrderPackage> checkClassExistCustomer(Long class_id, Long customer_id);

    @Modifying
    @Query(value = "SELECT op.* FROM `order` as o join order_package as op on o.id = op.order_id where o.user_id = ?2 and o.class_id = ?1", nativeQuery = true) // if
                                                                                                                                                                // want
                                                                                                                                                                // to
                                                                                                                                                                // write
                                                                                                                                                                // nativequery
                                                                                                                                                                // then
                                                                                                                                                                // mask
                                                                                                                                                                // nativeQuery
                                                                                                                                                                // as
                                                                                                                                                                // true
    List<OrderPackage> checkClassExistUser(Long class_id, Long userId);

    @Modifying
    @Query(value = "DELETE FROM order_package WHERE id = ?1", nativeQuery = true)
    int deleteProduct(Long id);

    @Query(value = "SELECT count(*) FROM `order` as o join order_package as op on o.id = op.order_id where o.status = ?1", nativeQuery = true)
    long countOrderByStatus(int status);

    @Query(value = "SELECT * FROM order_package WHERE order_id = ?1", nativeQuery = true)
    List<OrderPackage> countProduct(Long order_id);

    @Query(value = "SELECT count(*) FROM order_package WHERE package_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL (WEEKDAY(NOW()) + 7) DAY)) and created_date < DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
    long countOldPackage();

    @Query(value = "SELECT count(*) FROM order_package WHERE package_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
    long countNewPackage();

    @Query(value = "SELECT count(*) FROM order_package WHERE combo_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL (WEEKDAY(NOW()) + 7) DAY)) and created_date < DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
    long countOldCombo();

    @Query(value = "SELECT count(*) FROM order_package WHERE combo_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL WEEKDAY(NOW()) DAY))", nativeQuery = true)
    long countNewCombo();

    @Query(value = "SELECT count(*) FROM order_package WHERE combo_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL DAY(NOW()) DAY))", nativeQuery = true)
    long countMonthCombo();

    @Query(value = "SELECT count(*) FROM order_package WHERE package_id is not null and created_date >= DATE(DATE_SUB(NOW(), INTERVAL DAY(NOW()) DAY))", nativeQuery = true)
    long countMonthPackage();

}