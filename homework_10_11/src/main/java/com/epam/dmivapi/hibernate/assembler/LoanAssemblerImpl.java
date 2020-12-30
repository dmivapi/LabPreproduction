package com.epam.dmivapi.hibernate.assembler;

import com.epam.dmivapi.hibernate.dto.LoanDto;
import com.epam.dmivapi.hibernate.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanAssemblerImpl implements LoanAssembler {
    @Override
    public Loan assemble(LoanDto loanDto) {
        Loan loan = new Loan();
        // TODO double check if we need this loan.setId(loanDto.getId());
        loan.setDateOut(loanDto.getDateOut());
        loan.setDueDate(loanDto.getDueDate());
        loan.setDateIn(loanDto.getDateIn());
        loan.setReadingRoom(loanDto.isReadingRoom());

        return loan;
    }

    @Override
    public LoanDto assemble(Loan loan) {
        LoanDto loanDto = new LoanDto();
        loanDto.setId(loan.getId());
        loanDto.setDateOut(loan.getDateOut());
        loanDto.setDueDate(loan.getDueDate());
        loanDto.setDateIn(loan.getDateIn());
        loanDto.setReadingRoom(loan.isReadingRoom());
        loanDto.setBookCopyId(loan.getBookCopy().getId());
        return loanDto;
    }
}
