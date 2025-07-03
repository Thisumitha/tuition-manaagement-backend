package org.example.service;

import org.example.dto.TeacherDto;
import org.example.entity.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeacher() {
        TeacherDto dto = TeacherDto.builder().name("John").subject("Math").email("a@b.com").phone("123").build();
        TeacherEntity entity = TeacherEntity.builder().name("John").subject("Math").email("a@b.com").phone("123").build();
        TeacherEntity saved = TeacherEntity.builder().id(1L).name("John").subject("Math").email("a@b.com").phone("123").build();
        when(teacherRepository.save(any(TeacherEntity.class))).thenReturn(saved);
        TeacherDto result = teacherService.createTeacher(dto);
        assertEquals(saved.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());
    }

    @Test
    void testUpdateTeacher() {
        TeacherEntity entity = TeacherEntity.builder().id(1L).name("John").subject("Math").email("a@b.com").phone("123").build();
        TeacherDto dto = TeacherDto.builder().name("Jane").subject("Science").email("b@c.com").phone("456").build();
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(teacherRepository.save(any(TeacherEntity.class))).thenReturn(entity);
        TeacherDto result = teacherService.updateTeacher(1L, dto);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getSubject(), result.getSubject());
    }

    @Test
    void testUpdateTeacher_NotFound() {
        TeacherDto dto = TeacherDto.builder().name("Jane").build();
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> teacherService.updateTeacher(1L, dto));
    }

    @Test
    void testDeleteTeacher() {
        doNothing().when(teacherRepository).deleteById(1L);
        teacherService.deleteTeacher(1L);
        verify(teacherRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetTeacherById() {
        TeacherEntity entity = TeacherEntity.builder().id(1L).name("John").subject("Math").email("a@b.com").phone("123").build();
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(entity));
        TeacherDto result = teacherService.getTeacherById(1L);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
    }

    @Test
    void testGetTeacherById_NotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> teacherService.getTeacherById(1L));
    }

    @Test
    void testGetAllTeachers() {
        TeacherEntity entity = TeacherEntity.builder().id(1L).name("John").subject("Math").email("a@b.com").phone("123").build();
        when(teacherRepository.findAll()).thenReturn(Collections.singletonList(entity));
        List<TeacherDto> result = teacherService.getAllTeachers();
        assertEquals(1, result.size());
        assertEquals(entity.getName(), result.get(0).getName());
    }

    @Test
    void testPayTeacher_Success() {
        TeacherEntity entity = TeacherEntity.builder().id(1L).wallet(100.0).build();
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(teacherRepository.save(any(TeacherEntity.class))).thenReturn(entity);
        teacherService.payTeacher(1L, 50.0);
        assertEquals(50.0, entity.getWallet());
    }

    @Test
    void testPayTeacher_AmountNegative() {
        assertThrows(IllegalArgumentException.class, () -> teacherService.payTeacher(1L, -10.0));
    }

    @Test
    void testPayTeacher_TeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> teacherService.payTeacher(1L, 10.0));
    }

    @Test
    void testPayTeacher_InsufficientFunds() {
        TeacherEntity entity = TeacherEntity.builder().id(1L).wallet(10.0).build();
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(entity));
        assertThrows(IllegalStateException.class, () -> teacherService.payTeacher(1L, 20.0));
    }

    @Test
    void testGetAllTeachersWithWallets() {
        TeacherEntity entity = TeacherEntity.builder().id(1L).name("John").wallet(100.0).build();
        when(teacherRepository.findAll()).thenReturn(Collections.singletonList(entity));
        List<TeacherDto> result = teacherService.getAllTeachersWithWallets();
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getWallet());
    }
}
