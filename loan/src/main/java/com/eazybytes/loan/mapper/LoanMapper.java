package com.eazybytes.loan.mapper;

import com.eazybytes.loan.dto.LoanDto;
import com.eazybytes.loan.entity.Loan;

public class LoanMapper {
  public static LoanDto mapToLoanDto(Loan loan, LoanDto loanDto) {
    loanDto.setLoanNumber(loan.getLoanNumber());
    loanDto.setLoanType(loan.getLoanType());
    loanDto.setMobileNumber(loan.getMobileNumber());
    loanDto.setTotalAmount(loan.getTotalAmount());
    loanDto.setAmountPaid(loan.getAmountPaid());
    return loanDto;
  }

  public static Loan mapToLoan(LoanDto loanDto, Loan loan) {
    loan.setLoanNumber(loanDto.getLoanNumber());
    loan.setLoanType(loanDto.getLoanType());
    loan.setMobileNumber(loanDto.getMobileNumber());
    loan.setTotalAmount(loanDto.getTotalAmount());
    loan.setAmountPaid(loanDto.getAmountPaid());
    return loan;
  }
}
