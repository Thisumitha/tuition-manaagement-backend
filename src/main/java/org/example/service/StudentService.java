package org.example.service;

import org.example.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDTO);
    List<StudentDto> getAllStudents();
    StudentDto getStudentById(Long id);
    StudentDto updateStudent(Long id, StudentDto studentDTO);
    void deleteStudent(Long id);
}
