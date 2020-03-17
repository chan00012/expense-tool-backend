package com.ibm.expensetool.core.service;

import com.ibm.expensetool.client.expensedb.constants.BookType;
import com.ibm.expensetool.client.expensedb.model.Expense;
import com.ibm.expensetool.client.expensedb.model.User;
import com.ibm.expensetool.client.expensedb.repository.UserRepository;
import com.ibm.expensetool.client.reportsgeneration.dto.ReportForm;
import com.ibm.expensetool.utils.Rest;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@EnableScheduling
public class ReportService {

    @Value("${report.source}")
    private String reportSourceDirectory;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron ="${cron-daily-reports}")
    public void dailyReportSync() throws IOException {
        List<ReportForm> reportForms = extractDailyReport();
        dumpReportFile(reportForms);
    }

    public List<ReportForm> extractDailyReport() {

        List<ReportForm> reportForms = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        userList.forEach(ul -> {
            Long userId = ul.getUserId();
            String name = ul.getFirstName() + " " + ul.getLastName();
            String email = ul.getEmail();

            List<Expense> expenses = ul.getExpenses();
            List<Expense> dailyExpense = expenses.stream()
                                                            .filter(expense -> expense.getDateRecorded().before(new Date()))
                                                            .collect(Collectors.toList());

            dailyExpense.forEach(de -> {
                                            BookType bookType = de.getBookType();
                                            BigDecimal amount = de.getAmount();
                                            Date dateRecorded = de.getDateRecorded();

                                            ReportForm reportForm = new ReportForm(userId, name, email, bookType, amount, dateRecorded);
                                            reportForms.add(reportForm);
            });
        });
        return reportForms;
    }

    public void dumpReportFile(List<ReportForm> reportForms) throws IOException {

        String dateAppender = new SimpleDateFormat("MM-dd-yyyy").format(new Date());

        File directory = new File(reportSourceDirectory);
        if(!directory.exists()) directory.mkdir();

        String filename = reportSourceDirectory +  "\\reports_" + dateAppender + ".txt";

        File newFile = new File(filename);
        if(!newFile.exists()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (ReportForm reportForm : reportForms) {
                writer.write(Rest.serializeReport(reportForm));
                writer.newLine();
            }
            writer.close();
        }
    }
}
