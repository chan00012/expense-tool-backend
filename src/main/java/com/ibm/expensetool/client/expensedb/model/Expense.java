package com.ibm.expensetool.client.expensedb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.expensetool.client.expensedb.constants.BookType;
import com.ibm.expensetool.client.expensedb.constants.ExpenseType;
import com.ibm.expensetool.utils.Rest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EXPENSES_DATA")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long expenseId;

    private BigDecimal amount;

    private ExpenseType expenseType;

    private BookType bookType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @Temporal(TemporalType.DATE)
    private Date dateRecorded;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Expense(BigDecimal amount, ExpenseType expenseType, BookType bookType, User user) {
        this.amount = amount;
        this.expenseType = expenseType;
        this.bookType = bookType;
        this.user = user;
        this.dateRecorded = new Date();
    }

    public Expense(Long expenseId, BigDecimal amount, ExpenseType expenseType, BookType bookType, User user, Date dateRecorded) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.expenseType = expenseType;
        this.bookType = bookType;
        this.user = user;
        this.dateRecorded = dateRecorded;
    }

    public Expense(BigDecimal amount, ExpenseType expenseType, BookType bookType, User user, Date dateRecorded) {
        this.amount = amount;
        this.expenseType = expenseType;
        this.bookType = bookType;
        this.user = user;
        this.dateRecorded = dateRecorded;
    }

    @Override
    public String toString() {
        return Rest.toJsonString(this);
    }
}

