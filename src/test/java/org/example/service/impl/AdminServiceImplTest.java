package org.example.service.impl;

import org.example.dto.AdminDto;
import org.example.entity.AdminEntity;
import org.example.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {
    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAdmin() {
        AdminDto dto = new AdminDto(null, "admin", "admin@email.com", "pass", 0.0);
        AdminEntity entity = AdminEntity.builder().username("admin").email("admin@email.com").password("pass").build();
        AdminEntity saved = AdminEntity.builder().id(1L).username("admin").email("admin@email.com").password("pass").balance(0.0).build();
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(saved);
        AdminDto result = adminService.saveAdmin(dto);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getUsername(), result.getUsername());
    }

    @Test
    void testGetAllAdmins() {
        AdminEntity entity = AdminEntity.builder().id(1L).username("admin").email("admin@email.com").password("pass").balance(100.0).build();
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(entity));
        List<AdminDto> result = adminService.getAllAdmins();
        assertEquals(1, result.size());
        assertEquals(entity.getUsername(), result.get(0).getUsername());
    }

    @Test
    void testGetAdminById_Found() {
        AdminEntity entity = AdminEntity.builder().id(1L).username("admin").email("admin@email.com").password("pass").balance(100.0).build();
        when(adminRepository.findById(1L)).thenReturn(Optional.of(entity));
        AdminDto result = adminService.getAdminById(1L);
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
    }

    @Test
    void testGetAdminById_NotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
        AdminDto result = adminService.getAdminById(1L);
        assertNull(result);
    }

    @Test
    void testDeleteAdmin() {
        doNothing().when(adminRepository).deleteById(1L);
        adminService.deleteAdmin(1L);
        verify(adminRepository, times(1)).deleteById(1L);
    }

    @Test
    void testCalculateTotalProfit() {
        AdminEntity entity1 = AdminEntity.builder().id(1L).balance(100.0).build();
        AdminEntity entity2 = AdminEntity.builder().id(2L).balance(200.0).build();
        when(adminRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        double profit = adminService.calculateTotalProfit();
        assertEquals(300.0, profit);
    }

    @Test
    void testLogin_Success() {
        AdminEntity entity = AdminEntity.builder().id(1L).username("admin").email("admin@email.com").password("pass").balance(100.0).build();
        when(adminRepository.findByEmailAndPassword("admin@email.com", "pass")).thenReturn(Optional.of(entity));
        AdminDto result = adminService.login("admin@email.com", "pass");
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getUsername(), result.getUsername());
        assertNull(result.getPassword());
    }

    @Test
    void testLogin_Failure() {
        when(adminRepository.findByEmailAndPassword("admin@email.com", "wrong")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> adminService.login("admin@email.com", "wrong"));
    }
}
