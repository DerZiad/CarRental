package com.coding.app.repository;

import com.coding.app.models.User;
import com.coding.app.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    List<VerificationCode> findVerificationCodeByUser(User user);
}