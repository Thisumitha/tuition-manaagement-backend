package org.example.service;

import org.example.dto.AdminDto;

import java.util.List;

public interface AdminService {
    AdminDto saveAdmin(AdminDto dto);
    List<AdminDto> getAllAdmins();
    AdminDto getAdminById(Long id);
    void deleteAdmin(Long id);
    double calculateTotalProfit();
    AdminDto login(String email, String password);

}
