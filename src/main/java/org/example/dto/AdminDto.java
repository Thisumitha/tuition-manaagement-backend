package org.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
