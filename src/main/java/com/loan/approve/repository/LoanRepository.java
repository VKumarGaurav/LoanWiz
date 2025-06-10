package com.loan.approve.repository;

import com.loan.approve.entity.Loan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}