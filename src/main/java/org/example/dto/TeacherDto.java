package org.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDto {
    private Long id;
    private String name;
    private String subject;
    private String email;
    private String phone;
    private  double wallet;
}
