package com.courses.server.repositories;

import com.courses.server.entity.Combo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo, Long> {
	Page<Combo> findAllByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageable);
}
