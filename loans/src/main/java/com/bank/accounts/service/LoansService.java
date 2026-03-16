package com.bank.accounts.service;

import com.bank.accounts.dto.LoansDto;
import com.bank.accounts.entity.Loans;
import com.bank.accounts.exception.LoanAlreadyExistsException;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.repository.LoansRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoansService {

    private LoansRepository loansRepository;

    @Transactional
    public void createLoan(LoansDto loansDto) {

        Optional<Loans> loanExisting = loansRepository.findByMobileNumber(loansDto.mobileNumber());

        if (loanExisting.isPresent()) {
            throw new LoanAlreadyExistsException("Loan/customer with mobile number "
                    + loansDto.mobileNumber() + " already exists");
        }

        Loans loan = new Loans();
        loan.setMobileNumber(loansDto.mobileNumber());
        loan.setLoanNumber(loansDto.loanNumber());
        loan.setLoanType(loansDto.loanType());
        loan.setTotalLoan(loansDto.totalLoan());
        loan.setAmountPaid(loansDto.amountPaid());
        loan.setOutstandingAmount(loansDto.outstandingAmount());

        loansRepository.save(loan);

    }

    public LoansDto fetchLoanByMobileNumber(String mobileNumber) {

        Loans loans =  loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Can not be possible to find a loan with the mobile number " + mobileNumber));

        return new LoansDto(
                loans.getMobileNumber(),
                loans.getLoanNumber(),
                loans.getLoanType(),
                loans.getTotalLoan(),
                loans.getAmountPaid(),
                loans.getOutstandingAmount()
        );

    }

    @Transactional
    public void updateLoan(String mobileNumber, final LoansDto loansDto) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Can not be possible to find a loan with the account number " + mobileNumber));

        loans.setLoanNumber(loansDto.loanNumber());
        loans.setLoanType(loansDto.loanType());
        loans.setTotalLoan(loansDto.totalLoan());
        loans.setAmountPaid(loansDto.amountPaid());
        loans.setOutstandingAmount(loansDto.outstandingAmount());

        loansRepository.save(loans);

    }

    @Transactional
    public void deleteLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Can not be possible to find a loan with the mobile number " + mobileNumber));

        loansRepository.delete(loans);

    }

}
