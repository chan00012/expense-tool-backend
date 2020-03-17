package com.ibm.expensetool.service;

import com.ibm.expensetool.client.expensedb.constants.BookType;
import com.ibm.expensetool.client.expensedb.model.Expense;
import com.ibm.expensetool.client.expensedb.constants.ExpenseType;
import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import com.ibm.expensetool.core.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@RunWith(SpringRunner.class)
public class ReportServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportService reportService;

    @Before
    public void setUpUserWithExpenses() throws ParseException {
        User userWithExpenses = new User(1L, "christian", "robles", "cstrobles@gmail.com", "thisisnotapassword");
        Expense e1 = new Expense(2L, new BigDecimal("10000"), ExpenseType.SALARY, BookType.CREDIT, userWithExpenses, new Date());
        Expense e2 = new Expense(3L, new BigDecimal("1000"), ExpenseType.BILLS, BookType.DEBIT, userWithExpenses, new Date());
        Expense e3 = new Expense(4L, new BigDecimal("100"), ExpenseType.TRANSPORT, BookType.DEBIT, userWithExpenses, new Date());

        List<Expense> expenses = new ArrayList<>();
        expenses.add(e1);
        expenses.add(e2);
        expenses.add(e3);

        userWithExpenses.setExpenses(expenses);

        List<User> users = new ArrayList<>();
        users.add(userWithExpenses);

        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void extractDailyReports_withUserHaveExpense() {
        assertThat(reportService.extractDailyReport()).isNotEmpty();

    }
}