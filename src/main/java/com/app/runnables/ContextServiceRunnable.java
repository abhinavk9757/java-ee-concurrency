/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.runnables;

import java.security.AccessController;
import javax.security.auth.Subject;

/**
 *
 * @author Abhinav
 */
public class ContextServiceRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread => " + Thread.currentThread().getName());
        Subject subject = Subject.getSubject(AccessController.getContext());
        System.out.println("Security Information from a normal thread " + subject);
    }
    
}
