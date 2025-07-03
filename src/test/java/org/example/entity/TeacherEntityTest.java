package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherEntityTest {
    @Test
    void testBuilderAndGetters() {
        TeacherEntity teacher = TeacherEntity.builder()
                .id(1L)
                .name("John Smith")
                .subject("Physics")
                .email("john@school.com")
                .phone("1234567890")
                .wallet(500.0)
                .build();

        assertEquals(1L, teacher.getId());
        assertEquals("John Smith", teacher.getName());
        assertEquals("Physics", teacher.getSubject());
        assertEquals("john@school.com", teacher.getEmail());
        assertEquals("1234567890", teacher.getPhone());
        assertEquals(500.0, teacher.getWallet());
    }

    @Test
    void testSetters() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setId(2L);
        teacher.setName("Jane Doe");
        teacher.setSubject("Chemistry");
        teacher.setEmail("jane@school.com");
        teacher.setPhone("0987654321");
        teacher.setWallet(600.0);

        assertEquals(2L, teacher.getId());
        assertEquals("Jane Doe", teacher.getName());
        assertEquals("Chemistry", teacher.getSubject());
        assertEquals("jane@school.com", teacher.getEmail());
        assertEquals("0987654321", teacher.getPhone());
        assertEquals(600.0, teacher.getWallet());
    }

    @Test
    void testNoArgsConstructor() {
        TeacherEntity teacher = new TeacherEntity();
        assertNotNull(teacher);
    }

    @Test
    void testAllArgsConstructor() {
        TeacherEntity teacher = new TeacherEntity(3L, "Alice", "Biology", "alice@school.com", "1112223333", 700.0);
        assertEquals(3L, teacher.getId());
        assertEquals("Alice", teacher.getName());
        assertEquals("Biology", teacher.getSubject());
        assertEquals("alice@school.com", teacher.getEmail());
        assertEquals("1112223333", teacher.getPhone());
        assertEquals(700.0, teacher.getWallet());
    }
}
