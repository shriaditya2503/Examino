package com.beanforge.examino.controller;

import com.beanforge.examino.entity.Student;
import com.beanforge.examino.repo.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/me")
    public Student getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
