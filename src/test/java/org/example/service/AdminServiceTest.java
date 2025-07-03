package org.example.service;

import org.example.dto.AdminDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminService = Mockito.mock(AdminService.class);
    }

    @Test
    void testSaveAdmin() {
        AdminDto dto = new AdminDto();
        when(adminService.saveAdmin(dto)).thenReturn(dto);

        AdminDto result = adminService.saveAdmin(dto);
        assertEquals(dto, result);
    }

    @Test
    void testGetAllAdmins() {
        when(adminService.getAllAdmins()).thenReturn(List.of());
        List<AdminDto> admins = adminService.getAllAdmins();
        assertNotNull(admins);
    }

    @Test
    void testGetAdminById() {
        AdminDto dto = new AdminDto();
        when(adminService.getAdminById(1L)).thenReturn(dto);

        AdminDto result = adminService.getAdminById(1L);
        assertEquals(dto, result);
    }

    @Test
    void testDeleteAdmin() {
        doNothing().when(adminService).deleteAdmin(1L);
        adminService.deleteAdmin(1L);
        verify(adminService, times(1)).deleteAdmin(1L);
    }

    @Test
    void testCalculateTotalProfit() {
        when(adminService.calculateTotalProfit()).thenReturn(100.0);
        double profit = adminService.calculateTotalProfit();
        assertEquals(100.0, profit);
    }

    @Test
    void testLogin() {
        AdminDto dto = new AdminDto();
        when(adminService.login("admin@example.com", "password")).thenReturn(dto);

        AdminDto result = adminService.login("admin@example.com", "password");
        assertEquals(dto, result);
    }
}