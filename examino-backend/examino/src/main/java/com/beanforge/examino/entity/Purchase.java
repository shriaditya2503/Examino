package com.beanforge.examino.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="student_id",nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="course_id",nullable = false)
    private Course course;

    private LocalDate purchaseDate;
    private LocalDate expiryDate;
}
