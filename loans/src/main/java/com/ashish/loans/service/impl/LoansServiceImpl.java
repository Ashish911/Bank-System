package com.ashish.loans.service.impl;

import com.ashish.loans.constants.LoansConstants;
import com.ashish.loans.dto.LoansDto;
import com.ashish.loans.entity.Loans;
import com.ashish.loans.exception.LoanAlreadyExistsException;
import com.ashish.loans.exception.ResourceNotFoundException;
import com.ashish.loans.mapper.LoanMapper;
import com.ashish.loans.repository.LoansRepository;
import com.ashish.loans.service.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansServiceImpl implements ILoansService {

    @Autowired
    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalCards = loansRepository.findByMobileNumber(mobileNumber);

        if (optionalCards.isPresent()) {
            throw new LoanAlreadyExistsException("Card already registered with mobile number "
                    + mobileNumber);
        }

        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        LoansDto dto = LoanMapper.mapToLoansDto(loan, new LoansDto());

        return dto;
    }

    @Override
    public boolean updateLoan(LoansDto dto) {
        boolean isUpdated = false;

        Loans loan = loansRepository.findByLoanNumber(dto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", dto.getLoanNumber())
        );

        LoanMapper.mapToLoans(dto, loan);
        loansRepository.save(loan);
        isUpdated = true;

        return isUpdated;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        loansRepository.deleteById(loan.getLoanId());

        return true;
    }
}
