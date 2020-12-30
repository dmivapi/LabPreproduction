package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.config.HibernateConfig;
import com.epam.dmivapi.hibernate.dto.LoanDto;
import com.epam.dmivapi.hibernate.dto.UserDto;
import com.epam.dmivapi.hibernate.entity.BookCopy;
import com.epam.dmivapi.hibernate.entity.Role;
import com.epam.dmivapi.hibernate.repository.BookCopyRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
class UserServiceIT {

    private final UserService userService;
    private final BookCopyRepository bookCopyRepository;

    private static final String[] BOOK_COPY_NAMES = {"On Java 8", "Cambridge dictionary", "Bible", "Capital"};
    private Map<String, BookCopy> bookCopies = Collections.emptyMap();

    @Autowired
    public UserServiceIT(UserService userService, BookCopyRepository bookCopyRepository) {
        this.userService = userService;
        this.bookCopyRepository = bookCopyRepository;
    }

    @BeforeEach
    public void initializeBookCopies() {
        List<BookCopy> initialList = bookCopyRepository.getAll();
        if (initialList.isEmpty()) {
            initialList = createBookCopies();
        }
        bookCopies = initialList.stream().collect(Collectors.toMap(BookCopy::getTitle, Function.identity()));
    }

    List<BookCopy> createBookCopies() {
        return Stream.of(BOOK_COPY_NAMES)
                .map(title -> bookCopyRepository.create(title))
                .collect(Collectors.toList());
    }

    @Test
    void create() {
        //Given
        UserDto userDto = createUserDto(RandomString.make(10) + "@" + RandomString.make(10) + ".com");

        //When
        UserDto createdUser = userService.create(userDto);

        //Then
        assertUser(userDto, createdUser, true);
    }

    private UserDto createUserDto(String email) {
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword("password" + email);
        userDto.setFirstName("Betty");
        userDto.setLastName("Simpson");
        userDto.setLocaleName("en");
        userDto.setUserRole(Role.READER);
        userDto.setBlocked(true);

        LoanDto firstLoan = new LoanDto();
        firstLoan.setBookCopyId(getBookCopyId(0));
        firstLoan.setDateOut(LocalDate.now().minusDays(5));
        firstLoan.setDueDate(LocalDate.now().plusDays(5));
        firstLoan.setReadingRoom(true);
        userDto.getLoans().add(firstLoan);

        LoanDto secondLoan = new LoanDto();
        secondLoan.setBookCopyId(getBookCopyId(1));
        secondLoan.setDateOut(LocalDate.now().minusDays(14));
        secondLoan.setDueDate(LocalDate.now().minusDays(10));
        secondLoan.setDateIn(LocalDate.now().minusDays(3));
        secondLoan.setReadingRoom(true);
        userDto.getLoans().add(secondLoan);

        return userDto;
    }

    @Test
    void get() {
        //Given
        UserDto userDto = createUserDto(RandomString.make(10) + "@" + RandomString.make(10) + ".com");
        UserDto createdUser = userService.create(userDto);

        //When
        UserDto foundUser = userService.get(createdUser.getEmail());

        //Then
        assertUser(createdUser, foundUser, false);
    }

    private void assertUser(UserDto expected, UserDto actual, boolean skipId) {
        if(!skipId) {
            assertEquals(expected.getId(), actual.getId());
        }
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getLocaleName(), actual.getLocaleName());
        assertEquals(expected.getUserRole(), actual.getUserRole());
        assertEquals(expected.isBlocked(), actual.isBlocked());

        assertThat(actual.getLoans().size(), is(expected.getLoans().size()));
        assertThat(actual.getLoans(), containsInAnyOrder(expected.getLoans().toArray()));
    }

    @Test
    void update() {
        //Given
        UserDto userDto = createUserDto(RandomString.make(10) + "@" + RandomString.make(10) + ".com");
        UserDto createdUser = userService.create(userDto);

        createdUser.setEmail("new-email@gmail.com");

        LoanDto firstLoan = createdUser.getLoans().get(0);
        firstLoan.setDateIn(LocalDate.now());
        firstLoan.setReadingRoom(false);
        firstLoan.setBookCopyId(getBookCopyId(2));

        createdUser.getLoans().remove(1);

        LoanDto newLoan = new LoanDto();
        newLoan.setDateOut(LocalDate.now());
        newLoan.setReadingRoom(false);
        newLoan.setBookCopyId(getBookCopyId(3));

        createdUser.getLoans().add(newLoan);

        //When
        UserDto updatedUser = userService.update(createdUser);

        //Then
        assertUser(createdUser, updatedUser, false);
    }

    @Test
    void delete() {
        //Given
        UserDto userDto = createUserDto(RandomString.make(10) + "@" + RandomString.make(10) + ".com");
        UserDto createdUser = userService.create(userDto);

        //When
        userService.delete(createdUser.getId());

        //Then
        assertThrows(NoResultException.class, () -> userService.get(createdUser.getEmail()));
    }

    private UUID getBookCopyId(int bookCopyIndex) {
        return bookCopies.get(BOOK_COPY_NAMES[bookCopyIndex]).getId();
    }
}