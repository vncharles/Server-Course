package com.courses.server.repositories;

import com.courses.server.entity.Slide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
    Page<Slide> findAllByStatus(int status, Pageable pageable);
}
