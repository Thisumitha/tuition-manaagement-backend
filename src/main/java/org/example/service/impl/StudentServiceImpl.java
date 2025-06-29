package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.StudentDto;
import org.example.entity.AdminEntity;
import org.example.entity.HallEntity;
import org.example.entity.StudentEntity;
import org.example.entity.TeacherEntity;
import org.example.repository.AdminRepository;
import org.example.repository.HallRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TeacherRepository;
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
    private final HallRepository hallRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

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
    @Transactional
    public void processStudentPayment(Long studentId, double amount) {
        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }

        if (amount > student.getWallet()) {
            throw new IllegalArgumentException("Payment amount exceeds due amount. Due: $" + student.getWallet());
        }

        // Deduct from student's wallet
        student.setWallet(student.getWallet() - amount);
        studentRepository.save(student);

        // Find the hall that the student is part of
        HallEntity hall = hallRepository.findAll().stream()
                .filter(h -> h.getStudentIds().contains(student.getId())) // Compare by ID
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hall not found for student ID: " + studentId));

        // Split payment
        double teacherShare = amount * 0.85;
        double adminShare = amount * 0.15;

        // Credit teacher
        TeacherEntity teacher = teacherRepository.findById(hall.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found for ID: " + hall.getTeacherId()));

        teacher.setWallet(teacher.getWallet() + teacherShare);
        teacherRepository.save(teacher);

        AdminEntity admin = adminRepository.findByUsername("admin") // Find the admin by username
                .orElseThrow(() -> new RuntimeException("Admin user 'admin' not found. Cannot add admin share."));

        admin.setBalance(admin.getBalance() + adminShare); // Add the admin share to their balance
        adminRepository.save(admin); // Save the updated AdminEntity


        // Optionally: log or store admin share
        System.out.println("Admin earned: " + adminShare + " from student ID: " + studentId);
    }




}
