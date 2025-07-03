package org.example.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AttendanceDtoTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        AttendanceDto dto = new AttendanceDto();
        dto.setId(1L);
        dto.setStudentId(2L);
        dto.setClassId(3L);
        dto.setClassName("Math");
        dto.setTimestamp(now);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getStudentId());
        assertEquals(3L, dto.getClassId());
        assertEquals("Math", dto.getClassName());
        assertEquals(now, dto.getTimestamp());
    }

    @Test
    void testNoArgsConstructor() {
        AttendanceDto dto = new AttendanceDto();
        assertNotNull(dto);
    }
}
