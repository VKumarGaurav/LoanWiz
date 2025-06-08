package com.loan.approve.repository;

import com.loan.approve.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Long> {
    List<LoanApplication> findByUserId(Long userId);

    @Query("SELECT la FROM LoanApplication la JOIN FETCH la.user WHERE la.id = :applicationId")
    Optional<LoanApplication> findByIdWithApplicant(@Param("applicationId") Long applicationId);
}
