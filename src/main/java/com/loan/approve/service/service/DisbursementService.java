package com.loan.approve.service.service;

import com.loan.approve.dto.DisbursementDTO;

public interface DisbursementService {

    DisbursementDTO initiateDisbursement(DisbursementDTO disbursementDTO);

    DisbursementDTO getDisbursementStatus(Long disbursementId);

    void updateDisbursementStatus(Long disbursementId, String status);
}
