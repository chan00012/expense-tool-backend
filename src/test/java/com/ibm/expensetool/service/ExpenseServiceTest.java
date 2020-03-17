package com.ibm.expensetool.service;

import com.ibm.expensetool.client.expensedb.constants.BookType;
import com.ibm.expensetool.client.expensedb.model.Expense;
import com.ibm.expensetool.client.expensedb.constants.ExpenseType;
import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.ExpenseRepository;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import com.ibm.expensetool.core.dto.request.ExpenseRequest;
import com.ibm.expensetool.core.dto.request.UserAcesss;
import com.ibm.expensetool.core.service.ExpenseService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static java.util.Optional.ofNullable;
import static org.mockito.ArgumentMatchers.any;

@Log4j2
@RunWith(SpringRunner.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserAcesss userAcesss;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    public void addExpense_thenReturnExpense() {

        Date recordedDate = null;

        User user = User.builder()
                .userId(1L)
                .firstName("christian")
                .lastName("robles")
                .email("cstrobles@gmail.com")
                .password("thisisnotapassword")
                .build();

        ExpenseRequest expenseRequest = ExpenseRequest.builder()
                .expenseType(ExpenseType.SALARY)
                .bookType(BookType.CREDIT)
                .amount(new BigDecimal("10000"))
                .dateRecorded(ofNullable(recordedDate).orElse(new Date()))
                .build();

        Mockito.when(userAcesss.getUserId()).thenReturn(user.getUserId());
        Mockito.when(userRepository.findByUserId(any(Long.class))).thenReturn(user);

        Expense expense = expenseService.addExpense(expenseRequest);

        log.debug("|EXPENSE ADDED| - {}", expense);

        Assert.notNull(expense);
    }
}
