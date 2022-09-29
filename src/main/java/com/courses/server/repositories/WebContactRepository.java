package com.courses.server.repositories;

import com.courses.server.entity.WebContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebContactRepository  extends JpaRepository<WebContact, Long> {

}
