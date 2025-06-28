package org.example.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HallDto {
    private Long id;
    private String className;
    private Long teacherId;
    private List<Long> studentIds;
    private String day;
    private String timeSlot;
    private Double classFee;
    private Double hallFeePercentage;
}
