package com.ibm.expensetool.core.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.expensetool.client.expensedb.constants.BookType;
import com.ibm.expensetool.client.expensedb.constants.ExpenseType;
import com.ibm.expensetool.utils.Rest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseRequest {

    @NotNull(message = "Expense type cannot be null.")
    private ExpenseType expenseType;

    @NotNull(message = "Book type cannot be null.")
    private BookType bookType;

    @NotNull(message = "amount cannot be null.")
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date dateRecorded;

    @Override
    public String toString() {
        return Rest.toJsonString(this);
    }
}
