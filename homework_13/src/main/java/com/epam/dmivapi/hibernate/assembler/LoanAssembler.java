package com.epam.dmivapi.hibernate.assembler;

import com.epam.dmivapi.hibernate.dto.LoanDto;
import com.epam.dmivapi.hibernate.entity.Loan;

public interface LoanAssembler {
    LoanDto assemble(Loan loan);
    Loan assemble(LoanDto loanDto);
}
