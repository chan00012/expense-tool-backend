package com.ibm.expensetool.client.reportsgeneration.dto;

import com.ibm.expensetool.client.expensedb.constants.BookType;
import com.ibm.expensetool.utils.Rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportForm {

    private Long userId;
    private String name;
    private String email;
    private BookType bookType;
    private BigDecimal amount;
    private String dateRecorded;

    @Override
    public String toString(){
        return Rest.toJsonString(this);
    }

   public ReportForm(Long userId, String name, String email, BookType bookType, BigDecimal amount, Date dateRecorded){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.bookType = bookType;
        this.amount = amount;
        this.dateRecorded = new SimpleDateFormat("MM-dd-yyyy").format(dateRecorded);
   }

}
