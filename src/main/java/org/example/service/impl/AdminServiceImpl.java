package org.example.service.impl;

import org.example.dto.AdminDto;
import org.example.entity.AdminEntity;
import org.example.repository.AdminRepository;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminDto saveAdmin(AdminDto dto) {
        AdminEntity entity = AdminEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        AdminEntity saved = adminRepository.save(entity);

        return new AdminDto(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getPassword(), saved.getBalance());
    }

    @Override
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(admin -> new AdminDto(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getPassword(), admin.getBalance()))
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto getAdminById(Long id) {
        Optional<AdminEntity> optional = adminRepository.findById(id);
        return optional.map(admin -> new AdminDto(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getPassword(), admin.getBalance()))
                .orElse(null);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public double calculateTotalProfit() {
        // Example logic: sum up all student wallet payments or income from attendance
        return adminRepository.findAll().stream()
                .mapToDouble(student -> Math.max(student.getBalance(), 0)) // if wallet stores payment made
                .sum();
    }
    public AdminDto login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password)
                .map(admin -> new AdminDto(
                        admin.getId(),
                        admin.getUsername(),
                        admin.getEmail(),
                        null, // Don't return password
                        admin.getBalance()
                ))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }


}
