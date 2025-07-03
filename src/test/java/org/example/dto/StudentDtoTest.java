package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDtoTest {
    @Test
    void testBuilderAndGetters() {
        StudentDto student = StudentDto.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@example.com")
                .phone("1234567890")
                .subject("Mathematics")
                .wallet(100.0)
                .build();
        assertEquals(1L, student.getId());
        assertEquals("John Doe", student.getFullName());
        assertEquals("john@example.com", student.getEmail());
        assertEquals("1234567890", student.getPhone());
        assertEquals("Mathematics", student.getSubject());
        assertEquals(100.0, student.getWallet());
    }

    @Test
    void testSetters() {
        StudentDto student = new StudentDto();
        student.setId(2L);
        student.setFullName("Jane Smith");
        student.setEmail("jane@example.com");
        student.setPhone("0987654321");
        student.setSubject("Science");
        student.setWallet(200.0);
        assertEquals(2L, student.getId());
        assertEquals("Jane Smith", student.getFullName());
        assertEquals("jane@example.com", student.getEmail());
        assertEquals("0987654321", student.getPhone());
        assertEquals("Science", student.getSubject());
        assertEquals(200.0, student.getWallet());
    }

    @Test
    void testNoArgsConstructor() {
        StudentDto student = new StudentDto();
        assertNotNull(student);
    }

    @Test
    void testAllArgsConstructor() {
        StudentDto student = new StudentDto(3L, "Alice", "alice@example.com", "1112223333", "English", 300.0);
        assertEquals(3L, student.getId());
        assertEquals("Alice", student.getFullName());
        assertEquals("alice@example.com", student.getEmail());
        assertEquals("1112223333", student.getPhone());
        assertEquals("English", student.getSubject());
        assertEquals(300.0, student.getWallet());
    }
}
