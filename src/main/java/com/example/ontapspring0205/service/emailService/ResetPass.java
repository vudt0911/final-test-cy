package com.example.ontapspring0205.service.emailService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPass {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
