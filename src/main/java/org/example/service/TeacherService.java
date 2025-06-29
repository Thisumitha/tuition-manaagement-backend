package org.example.service;

import org.example.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    TeacherDto createTeacher(TeacherDto TeacherDto);
    TeacherDto updateTeacher(Long id, TeacherDto TeacherDto);
    void deleteTeacher(Long id);
    TeacherDto getTeacherById(Long id);
    List<TeacherDto> getAllTeachers();
    void payTeacher(Long teacherId, double amount);
    List<TeacherDto> getAllTeachersWithWallets();

}
