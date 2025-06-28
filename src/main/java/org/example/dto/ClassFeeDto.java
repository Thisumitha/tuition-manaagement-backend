package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassFeeDto {
    private Long id;
    private Long studentId;
    private String className; // Or classId if you prefer linking directly
    private Double amount;
    private LocalDate paymentDate;
    private String status; // e.g., "Paid", "Pending", "Overdue"
}