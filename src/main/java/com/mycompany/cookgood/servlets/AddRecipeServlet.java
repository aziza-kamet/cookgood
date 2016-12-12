/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cookgood.servlets;

import com.mycompany.cookgood.beans.DBConnection;
import com.mycompany.cookgood.beans.IngredientBean;
import com.mycompany.cookgood.beans.RecipeBean;
import com.mycompany.cookgood.beans.StepBean;
import com.mycompany.cookgood.beans.UserBean;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.taglibs.standard.extra.spath.Step;
/**
 *
 * @author Aziza
 */
@WebServlet(name = "AddRecipeServlet", urlPatterns = {"/add_recipe"})
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB 
                 maxFileSize=1024*1024*50,      	// 50 MB
                 maxRequestSize=1024*1024*100)   
public class AddRecipeServlet extends HttpServlet {

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
            if(request.getParameter("title") !=  null && request.getParameter("desc") != null && request.getParameter("categories") != null  && request.getParameter("cuisines") != null 
                    && request.getPart("image") != null && request.getParameter("ingredients") != null && request.getParameter("amount") != null && request.getParameter("measures") != null) {
    
                String title = request.getParameter("title");
                String desc = request.getParameter("desc");
                String category = request.getParameter("categories");
                String cuisine = request.getParameter("cuisines");                
                
                String applicationPath = request.getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + "images" + File.separator + "recipes";

                File fileSaveDir = new File(uploadFilePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }
                Part part = request.getPart("image");
                String imgName = part.getSubmittedFileName();
                String type = ".jpg";
                try {
                    type = imgName.split("\\.")[1];
                }catch(Exception ex){
                    Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String name = getHashedName(title + desc, type);                
                
                part.write(uploadFilePath + File.separator + name);  
                
                RecipeBean recipe = new RecipeBean();
                recipe.setTitle(title);
                recipe.setDesc(desc);
                recipe.setCategory(category);
                recipe.setCuisine(cuisine);
                recipe.setImage(name);
                
                String[] ingsArr = request.getParameterValues("ingredients");
                String[] amountArr = request.getParameterValues("amount");
                String[] measuresArr = request.getParameterValues("measures");
                
                if(ingsArr.length > 0 && amountArr.length > 0 && measuresArr.length > 0 
                        && ingsArr.length == amountArr.length && ingsArr.length == measuresArr.length) {
                    
                    int size = ingsArr.length;
                    ArrayList<IngredientBean> ings = new ArrayList<IngredientBean>();
                    
                    for(int i = 0; i < size; i++){
                        ings.add(new IngredientBean(ingsArr[i], amountArr[i], measuresArr[i]));
                    }
                    
                    recipe.setIngredients(ings);
                }           
                    
                ArrayList<StepBean> steps = new ArrayList<StepBean>();
                
                if(request.getPart("step_images") != null && request.getParameter("step_desc") != null){
                    
                    Collection<Part> parts = request.getParts();
                    String[] stepDescs = request.getParameterValues("step_desc");                    
                        
                    int i = 0;
                    for (Part p : parts) {

                        if(p.getName().equals("step_images")){
                            imgName = p.getSubmittedFileName();
                            name = getHashedName(imgName, imgName.split("\\.")[1]);
                            p.write(uploadFilePath + File.separator + name);
                            steps.add(new StepBean(name, stepDescs[i]));
                            i++;
                        }
                    }
                    
                    recipe.setSteps(steps);
                    
                }
                
                
                UserBean user = (UserBean)request.getSession(false).getAttribute("user");
                recipe.setUserID(user.getId());
                
                if(((DBConnection)request.getServletContext().getAttribute("dbBean")).addRecipe(recipe)){
                    response.sendRedirect("index.jsp?page=recipes");
                } else {
                    response.sendRedirect("index.jsp");
                }
            } else {
                out.print(request.getParameter("title"));
            }
            
        }
    }

    protected String getHashedName(String name, String type){
        
        String hash = name + "." + type;
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");            
            md.update(name.getBytes());
            byte byteData[] = md.digest();    

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            hash = sb.toString() + "." + type;

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hash;
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
