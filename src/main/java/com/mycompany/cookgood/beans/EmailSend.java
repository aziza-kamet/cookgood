/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package com.mycompany.cookgood.beans;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author Aziza
 */
public class EmailSend {

    Session session;
    Message message;
    Properties properties;
    
    public EmailSend(Properties p){
        this.properties = p;
    }
    
    public void createSession(){
        this.session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("username"), properties.getProperty("password"));
            }
        });
    }
    
    public Message createMessage() {
        if(session == null) createSession();
        message = new MimeMessage(session);
        return message;
    }
}
