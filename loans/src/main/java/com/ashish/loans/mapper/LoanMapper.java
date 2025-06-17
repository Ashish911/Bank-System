package com.ashish.loans.mapper;

import com.ashish.loans.dto.LoansDto;
import com.ashish.loans.entity.Loans;

public class LoanMapper {

    public static LoansDto mapToLoansDto(Loans loan, LoansDto dto) {
        dto.setMobileNumber(loan.getMobileNumber());
        dto.setLoanNumber(loan.getLoanNumber());
        dto.setLoanType(loan.getLoanType());
        dto.setTotalLoan(loan.getTotalLoan());
        dto.setAmountPaid(loan.getAmountPaid());
        dto.setOutstandingAmount(loan.getOutstandingAmount());
        return dto;
    }

    public static Loans mapToLoans(LoansDto dto, Loans loans) {
        loans.setMobileNumber(dto.getMobileNumber());
        loans.setLoanNumber(dto.getLoanNumber());
        loans.setLoanType(dto.getLoanType());
        loans.setTotalLoan(dto.getTotalLoan());
        loans.setAmountPaid(dto.getAmountPaid());
        loans.setOutstandingAmount(dto.getOutstandingAmount());
        return loans;
    }

}
