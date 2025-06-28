package org.example.repository;

import org.example.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    // Checks if an attendance record exists for a student in a class on a specific day
    boolean existsByStudentIdAndClassIdAndTimestampBetween(Long studentId, Long classId, LocalDateTime start, LocalDateTime end);

    // Finds all attendance records for a specific student in a specific class
    List<AttendanceEntity> findByStudentIdAndClassId(Long studentId, Long classId);

    // Counts all attendance records for a specific student in a specific class
    int countByStudentIdAndClassId(Long studentId, Long classId);

    /**
     * Finds all attendance records for a given class ID that occurred within a specific time window (e.g., a full day).
     *
     * @param classId The ID of the class.
     * @param start The start of the time window.
     * @param end The end of the time window.
     * @return A list of attendance entities.
     */
    List<AttendanceEntity> findByClassIdAndTimestampBetween(Long classId, LocalDateTime start, LocalDateTime end);

    /**
     * (Alternative to the above)
     * Finds all attendance records for a given class ID on a specific date.
     * This query correctly handles the date part of a timestamp.
     *
     * @param classId The ID of the class.
     * @param date The specific date to check.
     * @return A list of attendance entities.
     */
    @Query("SELECT a FROM AttendanceEntity a WHERE a.classId = :classId AND FUNCTION('DATE', a.timestamp) = :date")
    List<AttendanceEntity> findByClassIdAndDate(@Param("classId") Long classId, @Param("date") LocalDate date);
}