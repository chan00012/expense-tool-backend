package com.ibm.expensetool.repository;

import com.ibm.expensetool.client.expensedb.constants.BookType;
import com.ibm.expensetool.client.expensedb.model.Expense;
import com.ibm.expensetool.client.expensedb.constants.ExpenseType;
import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.ExpenseRepository;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Log4j2
@RunWith(SpringRunner.class)
@DataJpaTest
public class ExpsenseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    private User testUser = new User(1L,"christian", "robles", "cstrobles@gmail.com", "thisisnotapassword");


    @Before
    public void setUp() throws ParseException {
        User user = new User("christian", "robles", "cstrobles@gmail.com", "thisisnotapassword");
        User createdUser = entityManager.persist(user);

        Expense e1 = new Expense(new BigDecimal("10000"), ExpenseType.SALARY, BookType.CREDIT, createdUser, new Date(new SimpleDateFormat("MM-dd-yyyy").parse("03-15-2020").getTime()));
        Expense e2 = new Expense(new BigDecimal("1000"), ExpenseType.BILLS, BookType.DEBIT, createdUser, new Date(new SimpleDateFormat("MM-dd-yyyy").parse("03-16-2020").getTime()));
        Expense e3 = new Expense(new BigDecimal("100"), ExpenseType.TRANSPORT, BookType.DEBIT, createdUser, new Date(new SimpleDateFormat("MM-dd-yyyy").parse("03-17-2020").getTime()));

        entityManager.persist(e1);
        entityManager.persist(e2);
        entityManager.persist(e3);
    }


    @Test
    public void findExpensesByUser_thenReturnExpenses(){
        List<Expense> expenseExtracted = expenseRepository.findByUser(testUser);
        log.info("|EXPENSE EXTRACTED| - {}",expenseExtracted);
        Assert.notNull(expenseExtracted);
    }

    @Test
    public void findExpensesBySpecificDate_thenReturnExpenses() throws ParseException {
        List<Expense> expenseExtracted = expenseRepository.findByUserAndDateRecordedOrderByDateRecordedAsc(testUser, new Date(new SimpleDateFormat("MM-dd-yyyy").parse("03-15-2020").getTime()));
        Assert.notNull(expenseExtracted);
    }

    @Test
    public void findExpensesBySpecificDateRange_thenReturnExpenses() throws ParseException {
        List<Expense> expenseExtracted = expenseRepository.findByUserAndDateRecordedBetweenOrderByDateRecordedAsc(  testUser,
                                                                                                                    new Date(new SimpleDateFormat("MM-dd-yyy").parse("03-15-2020").getTime()),
                                                                                                                    new Date(new SimpleDateFormat("MM-dd-yyy").parse("03-17-2020").getTime())
                                                                                                                 );

       Assert.notNull(expenseExtracted);
    }
}
