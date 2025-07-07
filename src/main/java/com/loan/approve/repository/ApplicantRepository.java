package com.loan.approve.repository;

import com.loan.approve.entity.Applicant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,Long> {
    @Query("SELECT a FROM Applicant a LEFT JOIN FETCH a.financialProfile WHERE a.id = :applicantId")
    Optional<Applicant> findByIdWithFinancialData(@Param("applicantId") Long applicantId);

    // Alternative using EntityGraph (recommended)
    @EntityGraph(attributePaths = {"financialProfile"})
    Optional<Applicant> findWithFinancialProfileById(Long applicantId);
}
