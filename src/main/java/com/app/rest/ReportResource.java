/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.rest;

import com.app.beans.BankAccount;
import com.app.dao.BankAccountDao;
import com.app.runnables.ReportsProcessor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Abhinav
 */
@Path("reports")
public class ReportResource {
    private BankAccountDao dao;
    @Resource(lookup = "java:comp/DefaultManagedExecutorService")
    private ManagedExecutorService service;
    
    public ReportResource(){

//        InitialContext context;
//        try {
//            context = new InitialContext();
//            service = (ManagedExecutorService)context.lookup("java:comp/DefaultManagedExecutorService"); 
//        } catch (NamingException ex) {
//            Logger.getLogger(ReportResource.class.getName()).log(Level.SEVERE, null, ex);
//        }

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false");
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ReportResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dao = new BankAccountDao(dataSource);
    }
    
    @GET
    public String generateReports(){
        List<BankAccount> accounts = dao.getAllBankAccounts();
        for(BankAccount account: accounts){
            Future<Boolean> future = service.submit(new ReportsProcessor(account, dao));
            try {
                System.out.println("Report Generated => " + future.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ReportResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Report generation task submitted";
    }
}
