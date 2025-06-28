package org.example.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AttendanceDto {
    private Long id;
    private Long studentId;
    private Long classId;
    private String className;
    private LocalDateTime timestamp;
}
