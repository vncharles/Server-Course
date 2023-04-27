package com.courses.server.repositories;

import com.courses.server.entity.Type;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
	
	@Query(value="select * from Type where type_id = ?1", nativeQuery=true)
	Optional<Type> findbyType_id(int type);
}
