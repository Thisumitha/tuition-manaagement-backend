package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.StudentDto;
import org.example.entity.StudentEntity;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public StudentDto createStudent(StudentDto dto) {
        StudentEntity student = modelMapper.map(dto, StudentEntity.class);
        return modelMapper.map(studentRepository.save(student), StudentDto.class);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto dto) {
        StudentEntity existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        existing.setFullName(dto.getFullName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setSubject(dto.getSubject());
        return modelMapper.map(studentRepository.save(existing), StudentDto.class);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public long getStudentCount() {
        return studentRepository.count();
    }

    @Override
    public List<StudentDto> getUnpaidStudents() {
        return studentRepository.findAll().stream()
                .filter(student -> student.getWallet() > 0)
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }
    @Override
    @Transactional // Ensures the operation is atomic
    public void processStudentPayment(Long studentId, double amount) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }

        // Ensure the paid amount does not exceed the due amount
        if (amount > student.getWallet()) {
            throw new IllegalArgumentException("Payment amount exceeds the due amount. Due: $" + student.getWallet());
        }

        // Reduce the wallet amount by the paid amount
        student.setWallet(student.getWallet() - amount);

        studentRepository.save(student);
    }


}
