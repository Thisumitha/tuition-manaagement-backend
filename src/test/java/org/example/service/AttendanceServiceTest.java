package org.example.service;

import org.example.dto.AttendanceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AttendanceServiceTest {

    @Mock
    private AttendanceService attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarkAttendance() {
        AttendanceDto dto = new AttendanceDto();
        dto.setId(1L);
        dto.setStudentId(2L);
        dto.setClassId(3L);
        dto.setClassName("Math");
        dto.setTimestamp(LocalDateTime.now());
        when(attendanceService.markAttendance(any(AttendanceDto.class))).thenReturn(dto);
        AttendanceDto result = attendanceService.markAttendance(dto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAttendanceByStudentAndClass() {
        AttendanceDto dto1 = new AttendanceDto();
        AttendanceDto dto2 = new AttendanceDto();
        List<AttendanceDto> list = Arrays.asList(dto1, dto2);
        when(attendanceService.getAttendanceByStudentAndClass(2L, 3L)).thenReturn(list);
        List<AttendanceDto> result = attendanceService.getAttendanceByStudentAndClass(2L, 3L);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAttendanceCountForStudentInClass() {
        when(attendanceService.getAttendanceCountForStudentInClass(2L, 3L)).thenReturn(5);
        int count = attendanceService.getAttendanceCountForStudentInClass(2L, 3L);
        assertEquals(5, count);
    }

    @Test
    void testGetAttendanceForClassOnDate() {
        AttendanceDto dto = new AttendanceDto();
        List<AttendanceDto> list = Arrays.asList(dto);
        when(attendanceService.getAttendanceForClassOnDate(3L, "2025-07-03")).thenReturn(list);
        List<AttendanceDto> result = attendanceService.getAttendanceForClassOnDate(3L, "2025-07-03");
        assertEquals(1, result.size());
    }
}
