package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.TeacherDto;
import org.example.entity.TeacherEntity;
import org.example.repository.TeacherRepository;
import org.example.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public TeacherDto createTeacher(TeacherDto dto) {
        TeacherEntity entity = TeacherEntity.builder()
                .name(dto.getName())
                .subject(dto.getSubject())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();

        TeacherEntity saved = teacherRepository.save(entity);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public TeacherDto updateTeacher(Long id, TeacherDto dto) {
        TeacherEntity entity = teacherRepository.findById(id).orElseThrow();
        entity.setName(dto.getName());
        entity.setSubject(dto.getSubject());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        teacherRepository.save(entity);
        return dto;
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        TeacherEntity entity = teacherRepository.findById(id).orElseThrow();
        return TeacherDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .subject(entity.getSubject())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(entity -> TeacherDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .subject(entity.getSubject())
                        .email(entity.getEmail())
                        .phone(entity.getPhone())
                        .build())
                .collect(Collectors.toList());
    }
}
