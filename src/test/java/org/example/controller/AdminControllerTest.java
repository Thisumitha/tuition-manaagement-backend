package org.example.controller;

import org.example.dto.AdminDto;
import org.example.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock
    private AdminService adminService;
    @InjectMocks
    private AdminController adminController;

    @Test
    void testCreateAdmin() {
        AdminDto dto = new AdminDto(1L, "admin", "admin@email.com", "pass", 0.0);
        when(adminService.saveAdmin(dto)).thenReturn(dto);
        AdminDto result = adminController.createAdmin(dto);
        assertEquals(dto, result);
    }

    @Test
    void testGetAllAdmins() {
        AdminDto dto = new AdminDto(1L, "admin", "admin@email.com", "pass", 0.0);
        when(adminService.getAllAdmins()).thenReturn(Collections.singletonList(dto));
        List<AdminDto> result = adminController.getAllAdmins();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void testGetAdminById() {
        AdminDto dto = new AdminDto(1L, "admin", "admin@email.com", "pass", 0.0);
        when(adminService.getAdminById(1L)).thenReturn(dto);
        AdminDto result = adminController.getAdminById(1L);
        assertEquals(dto, result);
    }

    @Test
    void testDeleteAdmin() {
        doNothing().when(adminService).deleteAdmin(1L);
        adminController.deleteAdmin(1L);
        verify(adminService, times(1)).deleteAdmin(1L);
    }

    @Test
    void testGetTotalProfit() {
        when(adminService.calculateTotalProfit()).thenReturn(123.45);
        double profit = adminController.getTotalProfit();
        assertEquals(123.45, profit);
    }

    @Test
    void testLogin() {
        AdminDto dto = new AdminDto(1L, "admin", "admin@email.com", "pass", 0.0);
        when(adminService.login(dto.getEmail(), dto.getPassword())).thenReturn(dto);
        AdminDto result = adminController.login(dto);
        assertEquals(dto, result);
        //done
    }
}
