package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminDtoTest {
    @Test
    void testBuilderAndGetters() {
        AdminDto admin = new AdminDto(1L, "adminuser", "admin@email.com", "pass123", 100.0);
        assertEquals(1L, admin.getId());
        assertEquals("adminuser", admin.getUsername());
        assertEquals("admin@email.com", admin.getEmail());
        assertEquals("pass123", admin.getPassword());
        assertEquals(100.0, admin.getBalance());
    }

    @Test
    void testSetters() {
        AdminDto admin = new AdminDto();
        admin.setId(2L);
        admin.setUsername("user2");
        admin.setEmail("user2@email.com");
        admin.setPassword("pw2");
        admin.setBalance(200.0);
        assertEquals(2L, admin.getId());
        assertEquals("user2", admin.getUsername());
        assertEquals("user2@email.com", admin.getEmail());
        assertEquals("pw2", admin.getPassword());
        assertEquals(200.0, admin.getBalance());
    }

    @Test
    void testNoArgsConstructor() {
        AdminDto admin = new AdminDto();
        assertNotNull(admin);
    }
}
