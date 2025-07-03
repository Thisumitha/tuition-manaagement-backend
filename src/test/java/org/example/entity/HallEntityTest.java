package org.example.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HallEntityTest {

    @Test
    void testAllArgsConstructor() {
        List<Long> students = Arrays.asList(1L, 2L, 3L);

        HallEntity hall = new HallEntity(
                10L,
                "Mathematics Class",
                99L,
                students,
                "Monday",
                "08:00–09:00",
                1500.0,
                10.0
        );

        assertThat(hall.getId()).isEqualTo(10L);
        assertThat(hall.getClassName()).isEqualTo("Mathematics Class");
        assertThat(hall.getTeacherId()).isEqualTo(99L);
        assertThat(hall.getStudentIds()).isEqualTo(students);
        assertThat(hall.getDay()).isEqualTo("Monday");
        assertThat(hall.getTimeSlot()).isEqualTo("08:00–09:00");
        assertThat(hall.getClassFee()).isEqualTo(1500.0);
        assertThat(hall.getHallFeePercentage()).isEqualTo(10.0);
    }

    @Test
    void testBuilder() {
        List<Long> students = List.of(4L, 5L);

        HallEntity hall = HallEntity.builder()
                .id(11L)
                .className("Science Class")
                .teacherId(88L)
                .studentIds(students)
                .day("Tuesday")
                .timeSlot("09:00–10:00")
                .classFee(2000.0)
                .hallFeePercentage(12.5)
                .build();

        assertThat(hall.getId()).isEqualTo(11L);
        assertThat(hall.getClassName()).isEqualTo("Science Class");
        assertThat(hall.getTeacherId()).isEqualTo(88L);
        assertThat(hall.getStudentIds()).isEqualTo(students);
        assertThat(hall.getDay()).isEqualTo("Tuesday");
        assertThat(hall.getTimeSlot()).isEqualTo("09:00–10:00");
        assertThat(hall.getClassFee()).isEqualTo(2000.0);
        assertThat(hall.getHallFeePercentage()).isEqualTo(12.5);
    }

    @Test
    void testSetters() {
        HallEntity hall = new HallEntity();
        hall.setId(12L);
        hall.setClassName("English Class");
        hall.setTeacherId(77L);
        hall.setStudentIds(List.of(7L, 8L));
        hall.setDay("Wednesday");
        hall.setTimeSlot("10:00–11:00");
        hall.setClassFee(1800.0);
        hall.setHallFeePercentage(15.0);

        assertThat(hall.getId()).isEqualTo(12L);
        assertThat(hall.getClassName()).isEqualTo("English Class");
        assertThat(hall.getTeacherId()).isEqualTo(77L);
        assertThat(hall.getStudentIds()).containsExactly(7L, 8L);
        assertThat(hall.getDay()).isEqualTo("Wednesday");
        assertThat(hall.getTimeSlot()).isEqualTo("10:00–11:00");
        assertThat(hall.getClassFee()).isEqualTo(1800.0);
        assertThat(hall.getHallFeePercentage()).isEqualTo(15.0);
    }
}
