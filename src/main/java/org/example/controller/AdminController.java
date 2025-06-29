package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AdminDto;
import org.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
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

    @GetMapping("/profit")
    public double getTotalProfit() {
        return adminService.calculateTotalProfit(); // implement in service
    }
    @PostMapping("/login")
    public AdminDto login(@RequestBody AdminDto dto) {
        return adminService.login(dto.getEmail(), dto.getPassword());
    }


}
