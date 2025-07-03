package org.example.repository;

import org.example.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepositoryTest extends JpaRepository<TeacherEntity, Long> {
}
