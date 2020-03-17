package com.ibm.expensetool.core.service;

import com.ibm.expensetool.client.expensedb.model.Expense;
import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.ExpenseRepository;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import com.ibm.expensetool.core.constants.Frequency;
import com.ibm.expensetool.core.dto.request.ExpenseRequest;
import com.ibm.expensetool.core.dto.request.UserAcesss;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

@Log4j2
@Service
public class ExpenseService {

    @Autowired
    private UserAcesss userAcesss;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(ExpenseRequest expenseRequest) {

        User user = userRepository.findByUserId(userAcesss.getUserId());

        Expense expense = Expense.builder()
                .expenseType(expenseRequest.getExpenseType())
                .bookType(expenseRequest.getBookType())
                .amount(expenseRequest.getAmount())
                .dateRecorded(ofNullable(expenseRequest.getDateRecorded()).orElse(new Date()))
                .user(user)
                .build();

        expenseRepository.save(expense);
        log.info("|NEW EXPENSE ADDED| - {}", expense);
        return expense;
    }

    public List<Expense> getExpenses(Frequency frequency) {
        User user = userRepository.findByUserId(userAcesss.getUserId());

        List<Expense> expenses = new ArrayList<>();

        switch (frequency) {

            case DAILY      :       expenses = expenseRepository.findByUserAndDateRecordedOrderByDateRecordedAsc(user, new Date());
                                    break;
            case WEEKLY     :       expenses = getWeeklyExpenses(user);
                                    break;
            case MONTHLY    :       expenses = getMonthlyExpenses(user);
                                    break;
            case YEARLY     :       expenses = getYearlyExpenses(user);
                                    break;
            case ALL        :       expenses = expenseRepository.findByUser(user);
                                    break;
        }

        return expenses;
    }

    private List<Expense> getYearlyExpenses(User user) {
        List<Expense> expenses;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DATE, 31);
        Date endDate = calendar.getTime();
        log.info("start date:{}, end date:{}", startDate, endDate);
        expenses = expenseRepository.findByUserAndDateRecordedBetweenOrderByDateRecordedAsc(user, startDate, endDate);
        return expenses;
    }

    private List<Expense> getMonthlyExpenses(User user) {
        List<Expense> expenses;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date endDate = calendar.getTime();
        log.info("start date:{}, end date:{}", startDate, endDate);
        expenses = expenseRepository.findByUserAndDateRecordedBetweenOrderByDateRecordedAsc(user, startDate, endDate);
        return expenses;
    }

    private List<Expense> getWeeklyExpenses(User user) {
        List<Expense> expenses;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date endDate = calendar.getTime();
        log.info("start date:{}, end date:{}", startDate, endDate);
        expenses = expenseRepository.findByUserAndDateRecordedBetweenOrderByDateRecordedAsc(user, startDate, endDate);
        return expenses;
    }

}
