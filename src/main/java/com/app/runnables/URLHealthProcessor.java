/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.runnables;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abhinav
 */
public class URLHealthProcessor implements Runnable{
    private static final String urlName = "http://google.com";
    @Override
    public void run() {
        System.out.println("Thread name => " + Thread.currentThread().getName() + "is checking health of application");
        String statusApp = "";
        try {
            URL url = new URL(urlName);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            
            int responseCode = connection.getResponseCode();
            
            if(responseCode == 200){
                statusApp = "Working";
            } else {
                statusApp = "Not Working";
            }
            
            System.out.println("Status of app : "+ statusApp);
        } catch (MalformedURLException ex) {
            Logger.getLogger(URLHealthProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLHealthProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
