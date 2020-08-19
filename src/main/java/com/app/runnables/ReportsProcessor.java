/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.runnables;

import com.app.beans.BankAccount;
import com.app.beans.BankAccountTransaction;
import com.app.dao.BankAccountDao;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Abhinav
 */
public class ReportsProcessor implements Callable<Boolean>{
    private BankAccount account;
    private BankAccountDao dao;

    public ReportsProcessor(BankAccount account, BankAccountDao dao) {
        this.account = account;
        this.dao = dao;
    }

    @Override
    public Boolean call() throws Exception {
        Boolean reportGenerated = false;
        List<BankAccountTransaction> transactions = dao.getTransactionForAccount(account);
        File file = new File("D:/reports/" + account.getAccNumber()+ "_tx_report.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(BankAccountTransaction transaction: transactions){
                writer.write("Account Number : "+transaction.getAccNumber());
                writer.write(transaction.getTxType());
                writer.write(transaction.getTxId());
                writer.write("" + transaction.getAmount());
                writer.write("" + transaction.getTxDate());
                writer.newLine();
        }
        writer.flush();
        reportGenerated = true;
        return reportGenerated;
    }
}
