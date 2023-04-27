package com.courses.server.repositories;

import com.courses.server.entity.InfoContactCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoContactCourseRepository extends JpaRepository<InfoContactCourse, Long> {
}
