package org.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String subject;
    private  double wallet;
}
