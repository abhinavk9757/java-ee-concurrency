/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.dao;

import com.app.beans.BankAccount;
import com.app.beans.BankAccountTransaction;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abhinav
 */
public class BankAccountDao {
    private DataSource dataSource;

    public BankAccountDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<BankAccount> getAllBankAccounts(){
        Connection connection;
        List<BankAccount> accounts = new ArrayList<>();
        try {
            connection = this.dataSource.getConnection();
            
            BankAccount account = null;
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from bank_account");
            
            while(set.next()){
                account = new BankAccount();
                account.setAccNumber(set.getInt("acc_number"));
                account.setName("acc_holder_name");
                account.setEmail("acc_email");
                account.setAccType("acc_type");
                
                accounts.add(account);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }
    
    public List<BankAccountTransaction> getTransactionForAccount(BankAccount account){
        List<BankAccountTransaction> transactions = new ArrayList<>();
        try {
            Connection connection = this.dataSource.getConnection();
            BankAccountTransaction transaction = null;
            PreparedStatement statement = connection.prepareStatement("select * from bank_account_transaction where acc_number=?");
            statement.setInt(1, account.getAccNumber());
            ResultSet set = statement.executeQuery();
            
            while(set.next()){
                transaction = new BankAccountTransaction();
                transaction.setAccNumber(set.getInt("acc_number"));
                transaction.setAmount(set.getInt("amount"));
                transaction.setTxDate(new Date(set.getDate("transaction_date").getTime()));
                transaction.setTxId(set.getInt("tx_id"));
                transaction.setTxType(set.getString("transaction_type"));
                transactions.add(transaction);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return transactions;
    }
}
