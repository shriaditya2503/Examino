package com.beanforge.examino.repository;

import com.beanforge.examino.entity.RefreshToken;
import com.beanforge.examino.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByStudent(Student student);
}



