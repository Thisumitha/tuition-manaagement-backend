package org.example.repository;

import org.example.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositoryTest extends JpaRepository<StudentEntity, Long> {
}
