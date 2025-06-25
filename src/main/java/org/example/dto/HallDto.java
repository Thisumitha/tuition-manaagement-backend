package org.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallDto {
    private Long id;
    private String name;
    private int capacity;
    private String location;
}
