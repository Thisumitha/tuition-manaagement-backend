package org.example.controller;

import org.example.dto.AdminDto;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public AdminDto createAdmin(@RequestBody AdminDto dto) {
        return adminService.saveAdmin(dto);
    }

    @GetMapping
    public List<AdminDto> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminDto getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }
}
