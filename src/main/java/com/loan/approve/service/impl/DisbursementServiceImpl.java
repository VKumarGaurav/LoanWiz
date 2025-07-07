package com.loan.approve.service.impl;

import com.loan.approve.dto.DisbursementDTO;
import com.loan.approve.entity.Disbursement;
import com.loan.approve.exception.handlers.ResourceNotFoundException;
import com.loan.approve.repository.DisbursementRepository;
import com.loan.approve.service.service.DisbursementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DisbursementServiceImpl implements DisbursementService {

    @Autowired
    private DisbursementRepository disbursementRepository;

    @Override
    public DisbursementDTO initiateDisbursement(DisbursementDTO dto) {
        Disbursement disbursement = Disbursement.builder()
                .loanApplicationId(dto.getLoanApplicationId())
                .amount(dto.getAmount())
                .bankAccountNumber(dto.getBankAccountNumber())
                .status("PENDING")
                .disbursementDate(dto.getDisbursementDate())
                .build();
        disbursement = disbursementRepository.save(disbursement);
        dto.setId(disbursement.getId());
        dto.setStatus(disbursement.getStatus());
        return dto;
    }

    @Override
    public DisbursementDTO getDisbursementStatus(Long id) {
        Disbursement disbursement = disbursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement : "+id));
        return mapToDTO(disbursement);
    }

    @Override
    public void updateDisbursementStatus(Long id, String status) {
        Disbursement disbursement = disbursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement : "+id));
        disbursement.setStatus(status);
        disbursementRepository.save(disbursement);
    }

    private DisbursementDTO mapToDTO(Disbursement disbursement) {
        return DisbursementDTO.builder()
                .id(disbursement.getId())
                .loanApplicationId(disbursement.getLoanApplicationId())
                .amount(disbursement.getAmount())
                .bankAccountNumber(disbursement.getBankAccountNumber())
                .status(disbursement.getStatus())
                .disbursementDate(disbursement.getDisbursementDate())
                .build();
    }
}

