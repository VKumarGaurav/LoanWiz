package com.loan.approve.service.services;

import com.loan.approve.entity.LoanApplicationDocument;

import java.util.List;

public interface LoanSearchService {
    public LoanApplicationDocument save(LoanApplicationDocument doc);

    public List<LoanApplicationDocument> searchByApplicantName(String name);
}
