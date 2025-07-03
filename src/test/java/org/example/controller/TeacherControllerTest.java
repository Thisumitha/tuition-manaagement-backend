package org.example.controller;

import org.example.dto.TeacherDto;
import org.example.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {
    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private TeacherController teacherController;

    @Test
    void testCreateTeacher() {
        TeacherDto dto = TeacherDto.builder().id(1L).name("John").build();
        when(teacherService.createTeacher(dto)).thenReturn(dto);
        TeacherDto result = teacherController.createTeacher(dto);
        assertEquals(dto, result);
    }

    @Test
    void testUpdateTeacher() {
        TeacherDto dto = TeacherDto.builder().id(1L).name("Jane").build();
        when(teacherService.updateTeacher(1L, dto)).thenReturn(dto);
        TeacherDto result = teacherController.updateTeacher(1L, dto);
        assertEquals(dto, result);
    }

    @Test
    void testDeleteTeacher() {
        doNothing().when(teacherService).deleteTeacher(1L);
        teacherController.deleteTeacher(1L);
        verify(teacherService, times(1)).deleteTeacher(1L);
    }

    @Test
    void testGetTeacherById() {
        TeacherDto dto = TeacherDto.builder().id(1L).name("John").build();
        when(teacherService.getTeacherById(1L)).thenReturn(dto);
        TeacherDto result = teacherController.getTeacherById(1L);
        assertEquals(dto, result);
    }

    @Test
    void testGetAllTeachers() {
        TeacherDto dto = TeacherDto.builder().id(1L).name("John").build();
        when(teacherService.getAllTeachers()).thenReturn(Collections.singletonList(dto));
        List<TeacherDto> result = teacherController.getAllTeachers();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testPayTeacher_Success() {
        doNothing().when(teacherService).payTeacher(1L, 50.0);
        ResponseEntity<String> response = teacherController.payTeacher(1L, 50.0);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("paid amount $50.0"));
    }

    @Test
    void testPayTeacher_Failure() {
        doThrow(new RuntimeException("Insufficient funds")).when(teacherService).payTeacher(1L, 50.0);
        ResponseEntity<String> response = teacherController.payTeacher(1L, 50.0);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Payment failed: Insufficient funds"));
    }

    @Test
    void testGetAllWithWallets() {
        TeacherDto dto = TeacherDto.builder().id(1L).wallet(100.0).build();
        when(teacherService.getAllTeachersWithWallets()).thenReturn(Collections.singletonList(dto));
        ResponseEntity<List<TeacherDto>> response = teacherController.getAllWithWallets();
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void testGetTotalPendingPayments() {
        TeacherDto dto1 = TeacherDto.builder().id(1L).wallet(100.0).build();
        TeacherDto dto2 = TeacherDto.builder().id(2L).wallet(50.0).build();
        when(teacherService.getAllTeachersWithWallets()).thenReturn(List.of(dto1, dto2));
        ResponseEntity<Double> response = teacherController.getTotalPendingPayments();
        assertEquals(150.0, response.getBody());
    }
}
