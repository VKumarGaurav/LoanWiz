package com.loan.approve.repository;

import com.loan.approve.entity.LoanApplicationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationSearchRepository extends ElasticsearchRepository<LoanApplicationDocument, String> {
    List<LoanApplicationDocument> findByApplicantNameContaining(String name);
}
