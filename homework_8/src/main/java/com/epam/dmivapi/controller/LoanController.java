package com.epam.dmivapi.controller;

import com.epam.dmivapi.ContextParam;
import com.epam.dmivapi.Path;
import com.epam.dmivapi.config.LocaleConfig;
import com.epam.dmivapi.dto.LoanDto;
import com.epam.dmivapi.dto.LoanDtoViewAll;
import com.epam.dmivapi.model.CurrentUser;
import com.epam.dmivapi.service.LoanService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/loan")
@Log4j
public class LoanController {
    private LocaleConfig localeConfig;
    private LoanService loanService;
    private HttpSession session;

    @Autowired
    public LoanController(LocaleConfig localeConfig, LoanService loanService, HttpSession session) {
        this.localeConfig = localeConfig;
        this.loanService = loanService;
        this.session = session;
    }

    @RequestMapping("")
    public String getAllLoan(
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        log.debug("/loan invoked");
        final String GENRE_LANGUAGE_CODE = localeConfig.getCurrentLocale(session);

        List<LoanDtoViewAll> loans = loanService.getAllLoan(
                GENRE_LANGUAGE_CODE,
                currentPage,
                recordsPerPage
        );
        model.addAttribute(ContextParam.LS_LOANS, loans);

        int nOfPages = loanService.countLoans(
                GENRE_LANGUAGE_CODE,
                recordsPerPage
        );

        model.addAttribute(ContextParam.PGN_RECORDS_PER_PAGE, recordsPerPage);
        model.addAttribute(ContextParam.PGN_NUMBER_OF_PAGES, nOfPages);

        return Path.PAGE__LIST_LOANS_MULTIPLE_USERS;
    }

    @RequestMapping("/{userId}")
    public String getLoansByUserId(
            @PathVariable Integer userId,
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model
    ) {
        log.debug("/loan/" + userId + " invoked");
        final String GENRE_LANGUAGE_CODE = localeConfig.getCurrentLocale(session);

        List<LoanDto> loans = loanService.getLoansByUserId(
                userId,
                GENRE_LANGUAGE_CODE,
                currentPage,
                recordsPerPage
        );

        int nOfPages = loanService.countLoansByUserId(
                userId,
                GENRE_LANGUAGE_CODE,
                recordsPerPage
        );

        model.addAttribute(ContextParam.USER_ID_TO_PROCESS, userId);
        model.addAttribute(ContextParam.LS_LOANS, loans);
        model.addAttribute(ContextParam.PGN_RECORDS_PER_PAGE, recordsPerPage);
        model.addAttribute(ContextParam.PGN_NUMBER_OF_PAGES, nOfPages);

        return Path.PAGE__LIST_LOANS_SINGLE_USER;
    }

    @RequestMapping("/self")
    public String getLoansByCurrentUserId(
            Authentication authentication,
            @RequestParam(value = ContextParam.PGN_CURRENT_PAGE, defaultValue = "1") int currentPage,
            @RequestParam(value = ContextParam.PGN_RECORDS_PER_PAGE,
                    defaultValue = ContextParam.RECORDS_PER_PAGE) int recordsPerPage,
            Model model) {
        log.debug("/loan/self invoked");
        final String GENRE_LANGUAGE_CODE = localeConfig.getCurrentLocale(session);
        final Integer userId = ((CurrentUser)authentication.getPrincipal()).getId();

        List<LoanDto> loans = loanService.getLoansByUserId(
                userId,
                GENRE_LANGUAGE_CODE,
                currentPage,
                recordsPerPage
        );

        int nOfPages = loanService.countLoansByUserId(
                userId,
                GENRE_LANGUAGE_CODE,
                recordsPerPage
        );

        model.addAttribute(ContextParam.USER_ID_TO_PROCESS, userId);
        model.addAttribute(ContextParam.LS_LOANS, loans);
        model.addAttribute(ContextParam.PGN_RECORDS_PER_PAGE, recordsPerPage);
        model.addAttribute(ContextParam.PGN_NUMBER_OF_PAGES, nOfPages);

        return Path.PAGE__LIST_LOANS_SINGLE_USER;
    }

    @RequestMapping("/add")
    public String createLoansByUserIdAndPublicationsList(
            Authentication authentication,
            @RequestParam(ContextParam.PUBLICATIONS_IDS_TO_PROCESS) List<Integer> publicationIds,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage
            ) {
        log.debug("/loan/add invoked");
        loanService.createLoansByUserIdAndPublicationsList(
                ((CurrentUser)authentication.getPrincipal()).getId(),
                publicationIds
        );
        return "redirect:" + senderPage;
    }

    @RequestMapping("/out")
    public String updateLoanStatusToOutById(
            @RequestParam(ContextParam.LOAN_ID_TO_PROCESS) Integer loanId,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage
    ){
        log.debug("/loan/out invoked");
        loanService.updateLoanStatusToOutById(loanId);
        return "forward:" + senderPage;
    }

    @RequestMapping("/in")
    public String updateLoanStatusToReturnedById(
            @RequestParam(ContextParam.LOAN_ID_TO_PROCESS) Integer loanId,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage
    ){
        log.debug("/loan/in invoked");
        loanService.updateLoanStatusToReturnedById(loanId);
        return "forward:" + senderPage;
    }

    @RequestMapping("/delete")
    public String deleteLoanById(
            @RequestParam(ContextParam.LOAN_ID_TO_PROCESS) Integer loanId,
            @RequestParam(ContextParam.SELF_COMMAND) String senderPage
    ){
        log.debug("/loan/delete invoked");
        loanService.deleteLoanById(loanId);
        return "forward:" + senderPage;
    }
}
