package com.epam.dmivapi.hibernate.service;

import com.epam.dmivapi.hibernate.config.HibernateConfig;
import com.epam.dmivapi.hibernate.dto.LoanDto;
import com.epam.dmivapi.hibernate.dto.UserDto;
import com.epam.dmivapi.hibernate.entity.BookCopy;
import com.epam.dmivapi.hibernate.entity.Role;
import com.epam.dmivapi.hibernate.entity.User;
import com.epam.dmivapi.hibernate.repository.BookCopyRepository;
import net.bytebuddy.utility.RandomString;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class})
class HibernateSecondLevelCacheIT {

    private final UserService userService;
    private final BookCopyRepository bookCopyRepository;
    private final SessionFactory sessionFactory;

    private static final String[] BOOK_COPY_NAMES = {"On Java 8", "Cambridge dictionary", "Bible", "Capital"};
    private Map<String, BookCopy> bookCopies = Collections.emptyMap();

    @Autowired
    public HibernateSecondLevelCacheIT(UserService userService, BookCopyRepository bookCopyRepository, SessionFactory sessionFactory) {
        this.userService = userService;
        this.bookCopyRepository = bookCopyRepository;
        this.sessionFactory = sessionFactory;
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
    void loadWhenSecondLevelCacheIsOn() {
        //Given
        UserDto userDto = createUserDto(RandomString.make(10) + "@" + RandomString.make(10) + ".com");
        UserDto createdUser = userService.create(userDto);

        //When
        User user1 = sessionFactory.openSession().load(User.class, createdUser.getId());
        User user2 = sessionFactory.openSession().load(User.class, createdUser.getId());

        //Then
        assertNotSame(user1, user2);
        assertEquals(user1.getEmail(), user2.getEmail());
        assertEquals(printCacheInfo(), 1);
    }

    private static int printCacheInfo() {
        List<CacheManager> cacheManagers = CacheManager.ALL_CACHE_MANAGERS;
        if (!cacheManagers.isEmpty()) {
            CacheManager cacheManager = cacheManagers.get(0);
            Cache authorsCache = cacheManager.getCache(User.class.getName());
            return authorsCache.getSize();
        } else {
            return -1;
        }
    }

    private UUID getBookCopyId(int bookCopyIndex) {
        return bookCopies.get(BOOK_COPY_NAMES[bookCopyIndex]).getId();
    }
}