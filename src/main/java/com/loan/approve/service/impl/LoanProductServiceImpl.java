package com.loan.approve.service.impl;

import com.loan.approve.dto.LoanProductDTO;
import com.loan.approve.entity.LoanProduct;
import com.loan.approve.repository.LoanProductRepository;
import com.loan.approve.service.service.LoanProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoanProductServiceImpl implements LoanProductService {

    @Autowired private LoanProductRepository loanProductRepository;

    @Override
    public LoanProductDTO createLoanProduct(LoanProductDTO dto) {
        LoanProduct product = LoanProduct.builder()
                .productName(dto.getProductName())
                .minInterestRate(dto.getMinInterestRate())
                .termInMonths(dto.getMinTermMonths())
                .description(dto.getDescription())
                .build();
        product = loanProductRepository.save(product);
        dto.setId(product.getId());
        return dto;
    }

    @Override
    public LoanProductDTO getLoanProductById(Long id) {
        LoanProduct product = loanProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LoanProduct : " +id));
        return mapToDTO(product);
    }

    @Override
    public List<LoanProductDTO> getAllLoanProducts() {
        return loanProductRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LoanProductDTO updateLoanProduct(Long id, LoanProductDTO dto) {
        LoanProduct product = loanProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LoanProduct : " +id));
        product.setProductName(dto.getProductName());
        product.setMinInterestRate(dto.getMinInterestRate());
        product.setTermInMonths(dto.getMaxTermMonths());
        product.setDescription(dto.getDescription());
        product = loanProductRepository.save(product);
        return mapToDTO(product);
    }

    @Override
    public void deleteLoanProduct(Long id) {
        if (!loanProductRepository.existsById(id)) {
            throw new ResourceNotFoundException("LoanProduct : " +id);
        }
        loanProductRepository.deleteById(id);
    }

    private LoanProductDTO mapToDTO(LoanProduct product) {
        return LoanProductDTO.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .minInterestRate(product.getMinInterestRate())
                .minTermMonths(product.getTermInMonths())
                .description(product.getDescription())
                .build();
    }
}

