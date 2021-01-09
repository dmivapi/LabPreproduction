package com.epam.dmivapi.hibernate.assembler;

import com.epam.dmivapi.hibernate.dto.LoanDto;
import com.epam.dmivapi.hibernate.dto.UserDto;
import com.epam.dmivapi.hibernate.entity.BookCopy;
import com.epam.dmivapi.hibernate.entity.Loan;
import com.epam.dmivapi.hibernate.entity.User;
import com.epam.dmivapi.hibernate.repository.BookCopyRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class UserAssemblerImpl implements UserAssembler {
    LoanAssembler loanAssembler;
    BookCopyRepository bookCopyRepository;

    public UserAssemblerImpl(LoanAssembler loanAssembler, BookCopyRepository bookCopyRepository) {
        this.loanAssembler = loanAssembler;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public User assemble(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setLocaleName(userDto.getLocaleName());
        user.setUserRole(userDto.getUserRole());
        user.setBlocked(userDto.isBlocked());

        Map<UUID, BookCopy> bookCopyById = getBookCopies(userDto);

        for (LoanDto loanDto : userDto.getLoans()) {
             Loan loan = loanAssembler.assemble(loanDto);
             loan.setUser(user);

             BookCopy bookCopy = bookCopyById.get(loanDto.getBookCopyId());
             if (isNull(bookCopy)) {
                 throw new IllegalArgumentException("Book copy with id " + loanDto.getBookCopyId() + " does not exist");
             }
             loan.setBookCopy(bookCopy);

             user.getLoans().add(loan);
        }

        return user;
    }

    private Map<UUID, BookCopy> getBookCopies(UserDto dto) {
        List<UUID> bookCopyIds = dto.getLoans().stream().map(LoanDto::getBookCopyId).collect(Collectors.toList());
        List<BookCopy> bookCopies = bookCopyRepository.getByIds(bookCopyIds);
        return bookCopies.stream().collect(Collectors.toMap(BookCopy::getId, Function.identity()));
    }

    @Override
    public UserDto assemble(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setLocaleName(entity.getLocaleName());
        userDto.setUserRole(entity.getUserRole());
        userDto.setBlocked(entity.isBlocked());

        List<LoanDto> loans = entity.getLoans()
                .stream()
                .map(loanAssembler::assemble)
                .collect(Collectors.toList());

        userDto.setLoans(loans);

        return userDto;
    }
}
