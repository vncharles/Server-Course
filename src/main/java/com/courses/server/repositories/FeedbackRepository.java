package com.courses.server.repositories;

import com.courses.server.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findAllByStatusOrderByCreatedDateDesc(int status, Pageable pageable);

    @Query(value = "select * from feedback where combo_id = ?1 and status = 3 order by created_date desc", nativeQuery = true)
    Page<Feedback> getListFeedbackCombo(long combo_id, Pageable pageable);

    @Query(value = "select * from feedback where package_id = ?1 and status = 2 order by created_date desc", nativeQuery = true)
    Page<Feedback> getListFeedbackPackage(long package_id, Pageable pageable);

    @Query(value = "select * from feedback where expert_id = ?1 and status = 1 order by created_date desc", nativeQuery = true)
    Page<Feedback> getListFeedbackExpert(long expert_id, Pageable pageable);

    @Query(value = "select * from feedback where class_id = ?1 and status = 4 order by created_date desc", nativeQuery = true)
    Page<Feedback> getListFeedbackClass(long class_id, Pageable pageable);

    @Query(value = "select * from feedback where post_id = ?1 and status = 5 order by created_date desc", nativeQuery = true)
    Page<Feedback> getListFeedbackPost(long post_id, Pageable pageable);
}
