package com.loan.approve.service.impl;

import com.loan.approve.entity.LoanApplicationDocument;
import com.loan.approve.repository.LoanApplicationSearchRepository;
import com.loan.approve.service.service.LoanSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoanSearchServiceImpl implements LoanSearchService {

    @Autowired
    private LoanApplicationSearchRepository repository;

    public LoanApplicationDocument save(LoanApplicationDocument doc) {
        return repository.save(doc);
    }

    public List<LoanApplicationDocument> searchByApplicantName(String name) {
        return repository.findByApplicantNameContaining(name);
    }
}
