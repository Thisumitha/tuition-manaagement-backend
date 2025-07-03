package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HallEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;
    private Long teacherId;

    @ElementCollection
    private List<Long> studentIds;

    private String day;
    private String timeSlot;

    private Double classFee;
    private Double hallFeePercentage;

}
