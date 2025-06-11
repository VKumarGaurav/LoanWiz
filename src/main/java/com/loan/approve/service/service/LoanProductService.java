package com.loan.approve.service.service;


import com.loan.approve.dto.LoanProductDTO;

import java.util.List;

public interface LoanProductService {

    LoanProductDTO createLoanProduct(LoanProductDTO loanProductDTO);

    LoanProductDTO getLoanProductById(Long id);

    List<LoanProductDTO> getAllLoanProducts();

    LoanProductDTO updateLoanProduct(Long id, LoanProductDTO loanProductDTO);

    void deleteLoanProduct(Long id);
}
