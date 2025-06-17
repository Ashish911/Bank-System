package com.ashish.loans.service.impl;

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

@Service
public class LoansServiceImpl implements ILoansService {

    @Autowired
    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        LoansDto loansDto = new LoansDto();

        loansDto.setMobileNumber(mobileNumber);
        loansDto.setLoanNumber("10071469799154");
        loansDto.setLoanType("Credit");
        loansDto.setTotalLoan(-10000L);
        loansDto.setAmountPaid(-2000L);
        loansDto.setOutstandingAmount(-1000L);

        Loans loans = LoanMapper.mapToLoans(loansDto, new Loans());

        Optional<Loans> optionalCards = loansRepository.findByMobileNumber(loansDto.getMobileNumber());

        if (optionalCards.isPresent()) {
            throw new LoanAlreadyExistsException("Card already registered with mobile number "
                    + loansDto.getMobileNumber());
        }

        loansRepository.save(loans);
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

        Loans loan = loansRepository.findByMobileNumber(dto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", dto.getMobileNumber())
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
