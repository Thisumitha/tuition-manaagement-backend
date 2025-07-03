package org.example.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HallDtoTest {
    @Test
    void testBuilderAndGetters() {
        List<Long> students = Arrays.asList(1L, 2L, 3L);
        HallDto hall = HallDto.builder()
                .id(10L)
                .className("Math 101")
                .teacherId(5L)
                .studentIds(students)
                .day("Monday")
                .timeSlot("10:00-12:00")
                .classFee(200.0)
                .hallFeePercentage(10.0)
                .build();
        assertEquals(10L, hall.getId());
        assertEquals("Math 101", hall.getClassName());
        assertEquals(5L, hall.getTeacherId());
        assertEquals(students, hall.getStudentIds());
        assertEquals("Monday", hall.getDay());
        assertEquals("10:00-12:00", hall.getTimeSlot());
        assertEquals(200.0, hall.getClassFee());
        assertEquals(10.0, hall.getHallFeePercentage());
    }

    @Test
    void testSetters() {
        HallDto hall = new HallDto();
        List<Long> students = Arrays.asList(4L, 5L);
        hall.setId(20L);
        hall.setClassName("Science 202");
        hall.setTeacherId(6L);
        hall.setStudentIds(students);
        hall.setDay("Tuesday");
        hall.setTimeSlot("14:00-16:00");
        hall.setClassFee(300.0);
        hall.setHallFeePercentage(15.0);
        assertEquals(20L, hall.getId());
        assertEquals("Science 202", hall.getClassName());
        assertEquals(6L, hall.getTeacherId());
        assertEquals(students, hall.getStudentIds());
        assertEquals("Tuesday", hall.getDay());
        assertEquals("14:00-16:00", hall.getTimeSlot());
        assertEquals(300.0, hall.getClassFee());
        assertEquals(15.0, hall.getHallFeePercentage());
    }

    @Test
    void testNoArgsConstructor() {
        HallDto hall = new HallDto();
        assertNotNull(hall);
    }

    @Test
    void testAllArgsConstructor() {
        List<Long> students = Arrays.asList(7L, 8L);
        HallDto hall = new HallDto(30L, "English 303", 7L, students, "Wednesday", "16:00-18:00", 400.0, 20.0);
        assertEquals(30L, hall.getId());
        assertEquals("English 303", hall.getClassName());
        assertEquals(7L, hall.getTeacherId());
        assertEquals(students, hall.getStudentIds());
        assertEquals("Wednesday", hall.getDay());
        assertEquals("16:00-18:00", hall.getTimeSlot());
        assertEquals(400.0, hall.getClassFee());
        assertEquals(20.0, hall.getHallFeePercentage());
    }
}
