package com.loan.approve.repository;


import com.loan.approve.entity.Approval;
import com.loan.approve.util.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByLoanIdOrderByLevelAsc(Long loanId);
    List<Approval> findByApproverIdAndStatus(Long approverId, ApprovalStatus status);
}
