package com.ibm.expensetool.client.expensedb.repository;

import com.ibm.expensetool.client.expensedb.model.Expense;
import com.ibm.expensetool.client.expensedb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Expense findByExpenseId(Long expenseId);

    List<Expense> findByUser(User user);

    List<Expense> findByDateRecorded(Date dateRecorded);

    List<Expense> findByUserAndDateRecordedOrderByDateRecordedAsc(User user, Date dateRecorded);

    List<Expense> findByUserAndDateRecordedBetweenOrderByDateRecordedAsc(User user, Date startDate, Date endDate);


}
