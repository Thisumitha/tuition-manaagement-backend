package org.example.service;

import org.example.dto.StudentDto;
import org.example.entity.AdminEntity;
import org.example.entity.HallEntity;
import org.example.entity.StudentEntity;
import org.example.entity.TeacherEntity;
import org.example.repository.AdminRepository;
import org.example.repository.HallRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
import org.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private HallRepository hallRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        StudentDto dto = StudentDto.builder().id(1L).fullName("John").build();
        StudentEntity entity = StudentEntity.builder().id(1L).fullName("John").build();
        when(modelMapper.map(dto, StudentEntity.class)).thenReturn(entity);
        when(studentRepository.save(entity)).thenReturn(entity);
        when(modelMapper.map(entity, StudentDto.class)).thenReturn(dto);
        StudentDto result = studentService.createStudent(dto);
        assertEquals(dto, result);
    }

    @Test
    void testGetAllStudents() {
        StudentEntity entity = StudentEntity.builder().id(1L).fullName("John").build();
        StudentDto dto = StudentDto.builder().id(1L).fullName("John").build();
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(modelMapper.map(entity, StudentDto.class)).thenReturn(dto);
        List<StudentDto> result = studentService.getAllStudents();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetStudentById() {
        StudentEntity entity = StudentEntity.builder().id(1L).fullName("John").build();
        StudentDto dto = StudentDto.builder().id(1L).fullName("John").build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, StudentDto.class)).thenReturn(dto);
        StudentDto result = studentService.getStudentById(1L);
        assertEquals(dto, result);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    void testUpdateStudent() {
        StudentEntity entity = StudentEntity.builder().id(1L).fullName("John").email("a@b.com").phone("123").subject("Math").build();
        StudentDto dto = StudentDto.builder().id(1L).fullName("Jane").email("b@c.com").phone("456").subject("Science").build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(entity);
        when(modelMapper.map(entity, StudentDto.class)).thenReturn(dto);
        StudentDto result = studentService.updateStudent(1L, dto);
        assertEquals(dto, result);
    }

    @Test
    void testUpdateStudent_NotFound() {
        StudentDto dto = StudentDto.builder().id(1L).build();
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.updateStudent(1L, dto));
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetStudentCount() {
        when(studentRepository.count()).thenReturn(5L);
        assertEquals(5L, studentService.getStudentCount());
    }

    @Test
    void testGetUnpaidStudents() {
        StudentEntity entity = StudentEntity.builder().id(1L).wallet(100.0).build();
        StudentDto dto = StudentDto.builder().id(1L).wallet(100.0).build();
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(modelMapper.map(entity, StudentDto.class)).thenReturn(dto);
        List<StudentDto> result = studentService.getUnpaidStudents();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testProcessStudentPayment_Success() {
        StudentEntity student = StudentEntity.builder().id(1L).wallet(100.0).build();
        HallEntity hall = HallEntity.builder().id(1L).teacherId(2L).studentIds(Arrays.asList(1L)).build();
        TeacherEntity teacher = TeacherEntity.builder().id(2L).wallet(0.0).build();
        AdminEntity admin = AdminEntity.builder().id(3L).username("admin").balance(0.0).build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(hallRepository.findAll()).thenReturn(Collections.singletonList(hall));
        when(teacherRepository.findById(2L)).thenReturn(Optional.of(teacher));
        when(adminRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(student);
        when(teacherRepository.save(any(TeacherEntity.class))).thenReturn(teacher);
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(admin);
        studentService.processStudentPayment(1L, 20.0);
        assertEquals(80.0, student.getWallet());
        assertEquals(17.0, teacher.getWallet());
        assertEquals(3.0, admin.getBalance());
    }

    @Test
    void testProcessStudentPayment_StudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.processStudentPayment(1L, 10.0));
    }

    @Test
    void testProcessStudentPayment_HallNotFound() {
        StudentEntity student = StudentEntity.builder().id(1L).wallet(100.0).build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(hallRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(RuntimeException.class, () -> studentService.processStudentPayment(1L, 10.0));
    }

    @Test
    void testProcessStudentPayment_TeacherNotFound() {
        StudentEntity student = StudentEntity.builder().id(1L).wallet(100.0).build();
        HallEntity hall = HallEntity.builder().id(1L).teacherId(2L).studentIds(Arrays.asList(1L)).build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(hallRepository.findAll()).thenReturn(Collections.singletonList(hall));
        when(teacherRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.processStudentPayment(1L, 10.0));
    }

    @Test
    void testProcessStudentPayment_AdminNotFound() {
        StudentEntity student = StudentEntity.builder().id(1L).wallet(100.0).build();
        HallEntity hall = HallEntity.builder().id(1L).teacherId(2L).studentIds(Arrays.asList(1L)).build();
        TeacherEntity teacher = TeacherEntity.builder().id(2L).wallet(0.0).build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(hallRepository.findAll()).thenReturn(Collections.singletonList(hall));
        when(teacherRepository.findById(2L)).thenReturn(Optional.of(teacher));
        when(adminRepository.findByUsername("admin")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.processStudentPayment(1L, 10.0));
    }

    @Test
    void testProcessStudentPayment_AmountNegative() {
        StudentEntity student = StudentEntity.builder().id(1L).wallet(100.0).build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        assertThrows(IllegalArgumentException.class, () -> studentService.processStudentPayment(1L, -10.0));
    }

    @Test
    void testProcessStudentPayment_AmountExceedsWallet() {
        StudentEntity student = StudentEntity.builder().id(1L).wallet(10.0).build();
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        assertThrows(IllegalArgumentException.class, () -> studentService.processStudentPayment(1L, 20.0));
    }
}
