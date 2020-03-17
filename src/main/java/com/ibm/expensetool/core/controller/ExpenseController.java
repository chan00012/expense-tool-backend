package com.ibm.expensetool.core.controller;

import com.ibm.expensetool.client.expensedb.model.Expense;
import com.ibm.expensetool.core.constants.Frequency;
import com.ibm.expensetool.core.dto.request.ExpenseRequest;
import com.ibm.expensetool.core.service.ExpenseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add")
    public Expense addExpense(@Valid @RequestBody ExpenseRequest expenseRequest, @RequestHeader("x-session") String session) {
        log.info("|ADD EXPENSE REQUEST BODY| - {}", expenseRequest);
        Expense expense = expenseService.addExpense(expenseRequest);
        return expense;
    }

    @GetMapping("/pull/{frequecy}")
    public List<Expense> getUserExpenses(@PathVariable("frequecy") Frequency frequency, @RequestHeader("x-session") String session) {
        List<Expense> expenses = expenseService.getExpenses(frequency);
        return expenses;
    }

}
