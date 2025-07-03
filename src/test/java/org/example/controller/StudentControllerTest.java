package org.example.controller;

import org.example.dto.StudentDto;
import org.example.service.StudentService;
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
class StudentControllerTest {
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    @Test
    void testCreateStudent() {
        StudentDto dto = StudentDto.builder().id(1L).fullName("John").build();
        when(studentService.createStudent(dto)).thenReturn(dto);
        ResponseEntity<StudentDto> response = studentController.createStudent(dto);
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllStudents() {
        StudentDto dto = StudentDto.builder().id(1L).fullName("John").build();
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(dto));
        ResponseEntity<List<StudentDto>> response = studentController.getAllStudents();
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void testGetStudent() {
        StudentDto dto = StudentDto.builder().id(1L).fullName("John").build();
        when(studentService.getStudentById(1L)).thenReturn(dto);
        ResponseEntity<StudentDto> response = studentController.getStudent(1L);
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdateStudent() {
        StudentDto dto = StudentDto.builder().id(1L).fullName("Jane").build();
        when(studentService.updateStudent(1L, dto)).thenReturn(dto);
        ResponseEntity<StudentDto> response = studentController.updateStudent(1L, dto);
        assertEquals(dto, response.getBody());
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentService).deleteStudent(1L);
        ResponseEntity<Void> response = studentController.deleteStudent(1L);
        verify(studentService, times(1)).deleteStudent(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testGetStudentCount() {
        when(studentService.getStudentCount()).thenReturn(5L);
        ResponseEntity<Long> response = studentController.getStudentCount();
        assertEquals(5L, response.getBody());
    }

    @Test
    void testGetUnpaidStudents() {
        StudentDto dto = StudentDto.builder().id(1L).wallet(100.0).build();
        when(studentService.getUnpaidStudents()).thenReturn(Collections.singletonList(dto));
        List<StudentDto> result = studentController.getUnpaidStudents();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testMarkStudentAsPaid_Success() {
        doNothing().when(studentService).processStudentPayment(1L, 20.0);
        ResponseEntity<String> response = studentController.markStudentAsPaid(1L, 20.0);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Payment of $20.00 marked as completed for student ID: 1"));
    }

    @Test
    void testMarkStudentAsPaid_Failure() {
        doThrow(new RuntimeException("Insufficient funds")).when(studentService).processStudentPayment(1L, 20.0);
        ResponseEntity<String> response = studentController.markStudentAsPaid(1L, 20.0);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Payment failed: Insufficient funds"));
    }
}
