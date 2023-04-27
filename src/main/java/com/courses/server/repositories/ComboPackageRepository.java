package com.courses.server.repositories;

import com.courses.server.entity.ComboPackage;
import com.courses.server.entity.ComboPackageKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboPackageRepository extends JpaRepository<ComboPackage, ComboPackageKey> {
}
