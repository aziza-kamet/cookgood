/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cookgood.servlets;

import com.mycompany.cookgood.beans.DBConnection;
import com.mycompany.cookgood.beans.UserBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aziza
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String login = request.getParameter("login");
            String pwd = request.getParameter("pwd");
            String repwd = request.getParameter("repwd");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname"); 
            String hash = null;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");            
                md.update(login.getBytes());
                byte byteData[] = md.digest();    

                //convert the byte to hex format method 1
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < byteData.length; i++) {
                 sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }

                hash = sb.toString();
                
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            DBConnection db = (DBConnection) request.getServletContext().getAttribute("dbBean");
            boolean haveError = true;
            
            if(login != null && pwd != null && repwd != null && name != null && surname != null){
                                        
                if(!login.equals("") && !pwd.equals("") && !repwd.equals("") && !name.equals("") && !surname.equals("")){
                                    
                    if(!db.user_exists(login) && db.registration(login, pwd, repwd, name, surname, hash)){      
                                                
                        haveError = false;
                        UserBean user = new UserBean();                        
                        user.setLogin(login);
                        user.setPwd(pwd);
                        request.setAttribute("user", user);
                        request.setAttribute("hash", hash);
                        RequestDispatcher disp = request.getRequestDispatcher("mail");
                        disp.forward(request, response);    

                    } else {
                        response.sendRedirect(request.getContextPath() + "/");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
                
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }            
            
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
