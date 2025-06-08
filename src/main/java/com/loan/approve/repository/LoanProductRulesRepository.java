package com.loan.approve.repository;

import com.loan.approve.entity.LoanProductRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanProductRulesRepository extends JpaRepository<LoanProductRules, Long> {
    Optional<LoanProductRules> findByProductId(Long productId);
}
