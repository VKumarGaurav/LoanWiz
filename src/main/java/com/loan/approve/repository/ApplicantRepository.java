package com.loan.approve.repository;

import com.loan.approve.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,Long> {
    @Query("""
        SELECT la 
        FROM LoanApplication la 
        JOIN FETCH la.applicant a 
        JOIN FETCH a.financialData fd 
        WHERE a.id = :applicantId
    """)
    Optional<Object> findByIdWithFinancialData(@Param("applicantId") Long applicantId);
}
