package com.beanforge.examino.controller;

import com.beanforge.examino.dto.AuthResponse;
import com.beanforge.examino.dto.RefreshTokenRequest;
import com.beanforge.examino.entity.RefreshToken;
import com.beanforge.examino.entity.Student;
import com.beanforge.examino.repo.StudentRepository;
import com.beanforge.examino.security.JwtUtil;
import com.beanforge.examino.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          StudentRepository studentRepository,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return "Student registered successfully!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Student student) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(student.getEmail(), student.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }

        Student dbStudent = studentRepository.findByEmail(student.getEmail())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String accessToken = jwtUtil.generateToken(dbStudent.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(dbStudent);

        return new AuthResponse(accessToken, refreshToken.getToken(), dbStudent);
//             return AuthResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken.getToken())
//                .student(dbStudent)
//                .build();
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyExpiration(
                refreshTokenService
                        .createRefreshToken(
                                studentRepository.findByEmail(request.getEmail())
                                        .orElseThrow(() -> new RuntimeException("Student not found"))
                        )
        );

        Student student = refreshToken.getStudent();
        String newAccessToken = jwtUtil.generateToken(student.getEmail());

        return new AuthResponse(newAccessToken, refreshToken.getToken(), student);
    }
}



