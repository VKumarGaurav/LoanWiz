package com.loan.approve.repository;

import com.loan.approve.entity.RiskCalculationParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskParametersRepository extends JpaRepository<RiskCalculationParameters, Long> {
    Optional<RiskCalculationParameters> findByProductId(Long productId);
}