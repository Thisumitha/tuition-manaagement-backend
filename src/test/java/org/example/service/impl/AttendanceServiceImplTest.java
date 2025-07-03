package org.example.service.impl;

import org.example.dto.AttendanceDto;
import org.example.entity.AttendanceEntity;
import org.example.entity.HallEntity;
import org.example.entity.StudentEntity;
import org.example.entity.TeacherEntity;
import org.example.repository.AttendanceRepository;
import org.example.repository.HallRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceServiceImplTest {
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private HallRepository hallRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarkAttendance_Success() {
        AttendanceDto dto = new AttendanceDto();
        dto.setStudentId(1L);
        dto.setClassId(2L);
        HallEntity hall = HallEntity.builder().id(2L).className("Math").classFee(100.0).hallFeePercentage(10.0).teacherId(3L).build();
        when(hallRepository.findById(2L)).thenReturn(Optional.of(hall));
        when(attendanceRepository.existsByStudentIdAndClassIdAndTimestampBetween(anyLong(), anyLong(), any(), any())).thenReturn(false);
        when(attendanceRepository.countByStudentIdAndClassId(1L, 2L)).thenReturn(0);
        AttendanceEntity saved = AttendanceEntity.builder().id(10L).studentId(1L).classId(2L).timestamp(LocalDateTime.now()).build();
        when(attendanceRepository.save(any(AttendanceEntity.class))).thenReturn(saved);
        AttendanceDto result = attendanceService.markAttendance(dto);
        assertEquals(saved.getStudentId(), result.getStudentId());
        assertEquals("Math", result.getClassName());
    }

    @Test
    void testMarkAttendance_AlreadyMarked() {
        AttendanceDto dto = new AttendanceDto();
        dto.setStudentId(1L);
        dto.setClassId(2L);
        HallEntity hall = HallEntity.builder().id(2L).build();
        when(hallRepository.findById(2L)).thenReturn(Optional.of(hall));
        when(attendanceRepository.existsByStudentIdAndClassIdAndTimestampBetween(anyLong(), anyLong(), any(), any())).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> attendanceService.markAttendance(dto));
    }

    @Test
    void testMarkAttendance_ClassNotFound() {
        AttendanceDto dto = new AttendanceDto();
        dto.setStudentId(1L);
        dto.setClassId(2L);
        when(hallRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> attendanceService.markAttendance(dto));
    }

    @Test
    void testMarkAttendance_RequirePayment_InsufficientWallet() {
        AttendanceDto dto = new AttendanceDto();
        dto.setStudentId(1L);
        dto.setClassId(2L);
        HallEntity hall = HallEntity.builder().id(2L).classFee(100.0).hallFeePercentage(10.0).teacherId(3L).build();
        StudentEntity student = StudentEntity.builder().id(1L).wallet(50.0).build();
        when(hallRepository.findById(2L)).thenReturn(Optional.of(hall));
        when(attendanceRepository.existsByStudentIdAndClassIdAndTimestampBetween(anyLong(), anyLong(), any(), any())).thenReturn(false);
        when(attendanceRepository.countByStudentIdAndClassId(1L, 2L)).thenReturn(4);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        assertThrows(IllegalStateException.class, () -> attendanceService.markAttendance(dto));
    }

    @Test
    void testMarkAttendance_RequirePayment_Success() {
        AttendanceDto dto = new AttendanceDto();
        dto.setStudentId(1L);
        dto.setClassId(2L);
        HallEntity hall = HallEntity.builder().id(2L).classFee(100.0).hallFeePercentage(10.0).teacherId(3L).className("Math").build();
        StudentEntity student = StudentEntity.builder().id(1L).wallet(200.0).build();
        TeacherEntity teacher = TeacherEntity.builder().id(3L).wallet(0.0).build();
        when(hallRepository.findById(2L)).thenReturn(Optional.of(hall));
        when(attendanceRepository.existsByStudentIdAndClassIdAndTimestampBetween(anyLong(), anyLong(), any(), any())).thenReturn(false);
        when(attendanceRepository.countByStudentIdAndClassId(1L, 2L)).thenReturn(4);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(teacherRepository.findById(3L)).thenReturn(Optional.of(teacher));
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(student);
        when(teacherRepository.save(any(TeacherEntity.class))).thenReturn(teacher);
        AttendanceEntity saved = AttendanceEntity.builder().id(10L).studentId(1L).classId(2L).timestamp(LocalDateTime.now()).build();
        when(attendanceRepository.save(any(AttendanceEntity.class))).thenReturn(saved);
        AttendanceDto result = attendanceService.markAttendance(dto);
        assertEquals(saved.getStudentId(), result.getStudentId());
        assertEquals("Math", result.getClassName());
        assertEquals(100.0, student.getWallet());
        assertEquals(90.0, teacher.getWallet());
    }

    @Test
    void testGetAttendanceByStudentAndClass() {
        AttendanceEntity entity = AttendanceEntity.builder().id(1L).studentId(1L).classId(2L).build();
        when(attendanceRepository.findByStudentIdAndClassId(1L, 2L)).thenReturn(Collections.singletonList(entity));
        List<AttendanceDto> result = attendanceService.getAttendanceByStudentAndClass(1L, 2L);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getStudentId());
    }

    @Test
    void testGetAttendanceCountForStudentInClass() {
        when(attendanceRepository.countByStudentIdAndClassId(1L, 2L)).thenReturn(5);
        int count = attendanceService.getAttendanceCountForStudentInClass(1L, 2L);
        assertEquals(5, count);
    }

    @Test
    void testGetAttendanceForClassOnDate_Success() {
        AttendanceEntity entity = AttendanceEntity.builder().id(1L).classId(2L).timestamp(LocalDateTime.now()).build();
        when(attendanceRepository.findByClassIdAndTimestampBetween(anyLong(), any(), any())).thenReturn(Collections.singletonList(entity));
        List<AttendanceDto> result = attendanceService.getAttendanceForClassOnDate(2L, LocalDate.now().toString());
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getClassId());
    }

    @Test
    void testGetAttendanceForClassOnDate_InvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> attendanceService.getAttendanceForClassOnDate(2L, "invalid-date"));
    }
}
