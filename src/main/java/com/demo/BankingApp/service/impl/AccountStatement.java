package com.demo.BankingApp.service.impl;

import com.demo.BankingApp.repository.TransactionRepo;
import com.demo.BankingApp.service.EmailService;
import com.demo.BankingApp.service.UserService;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.demo.BankingApp.dto.AccountStatementDto;
import com.demo.BankingApp.dto.EmailDetails;
import com.demo.BankingApp.model.Transaction;
import com.demo.BankingApp.model.User;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import java.io.*;

import org.hibernate.internal.util.collections.Stack;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class AccountStatement {
    
    public TransactionRepo transactionRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private final static String FILE_LOCATION = "/Users/samuel/documents/BankingApp/statement.pdf";

    public List<Transaction> generateStatement(AccountStatementDto accountStatementDto) throws FileNotFoundException, DocumentException {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime from = LocalDate.parse(accountStatementDto.getStartDate(), formatter).atStartOfDay();
        LocalDateTime to = LocalDate.parse(accountStatementDto.getEndDate(), formatter).atStartOfDay();

        List<Transaction> transactions = transactionRepo.findByAccountNumber(accountStatementDto.getAccountNumber())
            .stream().filter(
                transaction -> (transaction.getTransactionDate().isEqual(from) || transaction.getTransactionDate().isAfter(from))
                    && (transaction.getTransactionDate().isEqual(to) || transaction.getTransactionDate().isBefore(to))
                )
                    .toList();

        // design account statement pdf
        designStatement(userService.getUserByAccountNumber(accountStatementDto.getAccountNumber()), transactions, from, to);

        return transactions;
    }

    public void designStatement(User user, List<Transaction> transactions, LocalDateTime startDate, LocalDateTime endDate) throws FileNotFoundException, DocumentException {
        // Rectangle rectangle = new Rectangle(PageSize.A4);
        Document doc  = new Document(PageSize.A4, 50, 50, 50, 50);
        // doc.setPageSize(rectangle);

        log.info("Setting size of document");

        FileOutputStream stream = new FileOutputStream(FILE_LOCATION);
        PdfWriter.getInstance(doc, stream);
        doc.open();

        PdfPTable bankInfoTable = new PdfPTable(1);

        // bank name
        PdfPCell bankName =  new PdfPCell(new Phrase("The MMBM Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        // address
        PdfPCell address = new PdfPCell(new Phrase("8, John Stones street, Manchester"));
        address.setBorder(0);

        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);

        // statement info
        PdfPTable statementInfo = new PdfPTable(2);

        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
        customerInfo.setBorder(0);

        PdfPCell endDateCell = new PdfPCell(new Phrase("End Date: " + endDate));
        endDateCell.setBorder(0);

        // customer info 
        PdfPCell name = new PdfPCell(new Phrase("Customer Name: " + user.getFullName()));
        name.setBorder(0);

        PdfPCell space = new PdfPCell();
        space.setBorder(0);

        PdfPCell customerAddress = new PdfPCell(new Phrase("Customer Address: " + user.getAddress()));
        customerAddress.setBorder(0);

        PdfPTable transactionsTable = new PdfPTable(4);

        // create table headers
        PdfPCell date = new PdfPCell(new Phrase("DATE"));
        date.setBackgroundColor(BaseColor.BLUE);
        date.setBorder(0);

        PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
        transactionType.setBackgroundColor(BaseColor.BLUE);
        transactionType.setBorder(0);

        PdfPCell transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
        transactionAmount.setBackgroundColor(BaseColor.BLUE);
        transactionAmount.setBorder(0);

        PdfPCell status = new PdfPCell(new Phrase("STATUS"));
        status.setBackgroundColor(BaseColor.BLUE);
        status.setBorder(0);

        // set bank info
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(space);
        bankInfoTable.addCell(statement);
        bankInfoTable.addCell(space);
        bankInfoTable.addCell(address);

        // set transaction table labels
        transactionsTable.addCell(date);
        transactionsTable.addCell(transactionType);
        transactionsTable.addCell(transactionAmount);
        transactionsTable.addCell(status);

        // set table values
        transactions.forEach(transaction -> {
            transactionsTable.addCell(new Phrase(transaction.getTransactionDate().toString()));
            transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
            transactionsTable.addCell(new Phrase("SUCCESS"));
        });

        // set statement info
        statementInfo.addCell(customerInfo);
        statementInfo.addCell(endDateCell);
        statementInfo.addCell(name);
        statementInfo.addCell(customerAddress);
        statementInfo.addCell(space);

        // add tables to document
        doc.add(bankInfoTable);
        doc.add(statementInfo);
        doc.add(transactionsTable);

        doc.close();

        EmailDetails details = EmailDetails.builder()
            .receipient(user.getEmail())
            .subject("STATEMENT OF ACCOUNT")
            .message("Find attached below a copy of the requested account statement")
            .attachment(FILE_LOCATION)
        .build();

        emailService.sendEmailWithAttachment(details);
    }
}
