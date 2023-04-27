package com.courses.server.repositories;
import com.courses.server.entity.Trainee;
import com.courses.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
//    Trainee findByUser(User user);

    long countAllByStatus(boolean status);

    Page<Trainee> findAllByUser(User user, Pageable pageable);

    @Modifying
    @Query(value = "SELECT * FROM trainee where class_id = ?1 and user_id = ?2", nativeQuery = true)
    List<Trainee> checkClassExistTraniee(Long class_id, Long userId);

    @Query(value = "SELECT t.* FROM trainee as t " +
            "join user as u on t.user_id = u.id " +
            "where u.fullname like %?1% or u.username like %?1% or u.email like %?1%", nativeQuery = true)
    Page<Trainee> getListTraineeKeyword(String keyword, Pageable pageable);

    @Query(value = "SELECT t.* FROM trainee as t " +
            "join user as u on t.user_id = u.id " +
            "where (u.fullname like %?1% or u.username like %?1% or u.email like %?1%) and t.status = ?2", nativeQuery = true)
    Page<Trainee> getListTraineeKeywordAndStatus(String keyword, boolean status, Pageable pageable);

    @Query(value = "select * from trainee where status = ?1", nativeQuery = true)
    Page<Trainee> getListTraineeByStatus(boolean status, Pageable pageable);

    @Query(value = "SELECT t.* FROM trainee as t " +
            "join user as u on t.user_id = u.id " +
            "where (u.fullname like %?1% or u.username like %?1% or u.email like %?1%) and class_id = ?2", nativeQuery = true)
    Page<Trainee> getListTraineeKeywordAndClass(String keyword, Long class_id, Pageable pageable);

    @Query(value = "SELECT t.* FROM trainee as t " +
            "join user as u on t.user_id = u.id " +
            "where (u.fullname like %?1% or u.username like %?1% or u.email like %?1%) and t.status = ?2  and class_id = ?3", nativeQuery = true)
    Page<Trainee> getListTraineeKeywordAndStatusAndClass(String keyword, boolean status, Long class_id, Pageable pageable);

    @Query(value = "select * from trainee where status = ?1  and class_id = ?2", nativeQuery = true)
    Page<Trainee> getListTraineeByStatusAndClass(boolean status, Long class_id, Pageable pageable);

    @Query(value = "select * from trainee where class_id = ?1", nativeQuery = true)
    Page<Trainee> getListTraineeByClass(Long class_id, Pageable pageable);

    List<Trainee> findByUser(User user);

    @Query(value = "SELECT COUNT(*) FROM trainee where status = 1", nativeQuery = true)
    long count_active_trainee_off();

    @Transactional
    @Modifying
    @Query(value = "update trainee set status = 0 where drop_out_date < DATE(NOW())", nativeQuery = true)
    void checkEndPage();
}
