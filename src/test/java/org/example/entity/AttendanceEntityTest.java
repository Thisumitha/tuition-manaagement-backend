package org.example.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceEntityTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDateTime time = LocalDateTime.now();
        AttendanceEntity attendance = new AttendanceEntity(1L, 100L, 200L, time);

        assertThat(attendance.getId()).isEqualTo(1L);
        assertThat(attendance.getStudentId()).isEqualTo(100L);
        assertThat(attendance.getClassId()).isEqualTo(200L);
        assertThat(attendance.getTimestamp()).isEqualTo(time);
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 1, 10, 0);

        AttendanceEntity attendance = AttendanceEntity.builder()
                .id(2L)
                .studentId(101L)
                .classId(201L)
                .timestamp(now)
                .build();

        assertThat(attendance.getId()).isEqualTo(2L);
        assertThat(attendance.getStudentId()).isEqualTo(101L);
        assertThat(attendance.getClassId()).isEqualTo(201L);
        assertThat(attendance.getTimestamp()).isEqualTo(now);
    }

    @Test
    void testSettersAndNoArgsConstructor() {
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setId(3L);
        attendance.setStudentId(102L);
        attendance.setClassId(202L);
        LocalDateTime time = LocalDateTime.now();
        attendance.setTimestamp(time);

        assertThat(attendance.getId()).isEqualTo(3L);
        assertThat(attendance.getStudentId()).isEqualTo(102L);
        assertThat(attendance.getClassId()).isEqualTo(202L);
        assertThat(attendance.getTimestamp()).isEqualTo(time);
    }
}
