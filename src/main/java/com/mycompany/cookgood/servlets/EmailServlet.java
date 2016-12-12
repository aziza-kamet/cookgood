/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cookgood.servlets;

import com.mycompany.cookgood.beans.EmailSend;
import com.mycompany.cookgood.beans.UserBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aziza
 */
public class EmailServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            PrintWriter out = response.getWriter();
            
            UserBean user = (UserBean) request.getAttribute("user");
            String hash = (String) request.getAttribute("hash");
            if(user != null){
                
                Properties p = System.getProperties();
                p.setProperty("mail.smtp.auth", "true");
                p.setProperty("mail.smtp.starttls.enable", "true");
                p.setProperty("mail.smtp.host", "smtp.gmail.com");
                p.setProperty("mail.smtp.port", "587");   
                p.setProperty("username", "cookgoodsite@gmail.com");
                p.setProperty("password", "asdfgh123456");
                EmailSend email = new EmailSend(p);
                MimeMessage message = (MimeMessage)email.createMessage();
                message.setSubject("CookGood | Registration confirmation");        


                message.setContent("<a href='cookgood.herokuapp.com/email_confirmation?hash=" + hash + "'>Click here for confirmation</a>","text/html");

                message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getLogin()));
                Transport.send(message);
            
                response.sendRedirect("index.jsp?page=letter_send");
                
            } else {
               
                response.sendRedirect("index.jsp?err=1");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
