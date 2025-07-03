package org.example.controller;

import org.example.dto.AttendanceDto;
import org.example.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {
    @Mock
    private AttendanceService attendanceService;
    @InjectMocks
    private AttendanceController attendanceController;

    @Test
    void testMarkAttendance() {
        AttendanceDto dto = new AttendanceDto();
        when(attendanceService.markAttendance(dto)).thenReturn(dto);
        AttendanceDto result = attendanceController.markAttendance(dto);
        assertEquals(dto, result);
    }

    @Test
    void testGetAttendanceCount() {
        when(attendanceService.getAttendanceCountForStudentInClass(1L, 2L)).thenReturn(5);
        int count = attendanceController.getAttendanceCount(1L, 2L);
        assertEquals(5, count);
    }

    @Test
    void testGetAttendanceRecords() {
        AttendanceDto dto = new AttendanceDto();
        when(attendanceService.getAttendanceByStudentAndClass(1L, 2L)).thenReturn(Collections.singletonList(dto));
        List<AttendanceDto> result = attendanceController.getAttendanceRecords(1L, 2L);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetAttendanceByDate() {
        AttendanceDto dto = new AttendanceDto();
        when(attendanceService.getAttendanceForClassOnDate(2L, "2024-01-01")).thenReturn(Collections.singletonList(dto));
        List<AttendanceDto> result = attendanceController.getAttendanceByDate(2L, "2024-01-01");
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
