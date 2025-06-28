package org.example.dto;

import lombok.*;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallDto {
    private Long id;
    private String className;
    private String teacher;
    private List<String> students;
    private String day;
    private String timeSlot;
}
