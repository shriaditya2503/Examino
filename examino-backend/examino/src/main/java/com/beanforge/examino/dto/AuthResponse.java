package com.beanforge.examino.dto;

import com.beanforge.examino.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Student student;

    public AuthResponse(String accessToken, String refreshToken, Student student) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.student = student;
    }
}

