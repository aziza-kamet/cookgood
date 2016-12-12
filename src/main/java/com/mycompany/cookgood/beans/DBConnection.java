/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cookgood.beans;

import java.io.Serializable;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aziza
 */
public class DBConnection implements Serializable{
    private Connection connection;
    
    public DBConnection(){
        
        try {
           
            Class.forName("org.postgresql.Driver");
            URI dbUri = new URI("postgres://zwjryexgjiofje:t_YOOGhUbAEhYp6z2LRIOikllz@ec2-23-23-211-21.compute-1.amazonaws.com:5432/dada3ij2mcptj6");

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

            this.connection = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connected");            
            
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
    }
    
    public UserBean authorization(String login, String password){
        UserBean user = null;
        try{
            
            String sql = "SELECT * FROM users WHERE login=? AND password=? AND active=1";            
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                user = new UserBean();
                user.setId(rs.getInt("u_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setAvatar(rs.getString("avatar"));
            }
            
        } catch (Exception ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
    
    public boolean registration(String login, String password, String rePassword, String name, String surname, String hash){
        try{
            
            if(password.equals(rePassword)){
            
                String sql = "INSERT INTO users (login, password, name, surname, user_hash) VALUES(?,?,?,?,?)";            
                PreparedStatement ps = this.connection.prepareStatement(sql);
                ps.setString(1, login);
                ps.setString(2, password);
                ps.setString(3, name);
                ps.setString(4, surname);
                ps.setString(5, hash);

                int i = ps.executeUpdate();
                                
                return true;                 
            }
        } catch (Exception ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean user_exists(String login){
                
        try {
            
            String sql = "SELECT * FROM users WHERE login = ? AND active = 1";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, login);            
            
            if(ps.executeQuery().next()){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;      
    }
    
    public UserBean getUserById(int id){
    
        UserBean user = null;
        
        try {
            
            String sql = "SELECT * FROM users WHERE u_id = ? AND active = 1";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new UserBean();
                user.setId(rs.getInt("u_id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setAvatar(rs.getString("avatar"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
    
    public UserBean getUserById(String id){
        
        return getUserById(Integer.parseInt(id));
    }
    
    public UserBean confirmRegistration(String hash){
        
        UserBean user = null;
        
        try {
            
            String sql = "UPDATE users SET active = 1 WHERE user_hash = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, hash);            
            
            if(ps.executeUpdate() == 1){
                
                sql = "SELECT login, password FROM users WHERE user_hash = ?";
                ps = this.connection.prepareStatement(sql);
                ps.setString(1, hash); 
                
                ResultSet rs = null;
                if((rs = ps.executeQuery()).next()){
                    user = new UserBean();
                    user.setLogin(rs.getString("login"));
                    user.setPwd(rs.getString("password"));
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
        
    }
    
    public ArrayList<String> getCatalog(String table){
        
        ArrayList<String> data = new ArrayList<String>();
        
        try {
            
            String sql = "SELECT * FROM " + table + " WHERE active = 1"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String temp = rs.getString("name");
                data.add(temp);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }   
    
    
    public boolean addRecipe(RecipeBean recipe){
        
        try {
            
            String sql = "INSERT INTO recipes (u_id, title, description, image, category, cuisine) VALUES (?, ?, ?, ?, ?, ?)"; 
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, recipe.getUserID());
            ps.setString(2, recipe.getTitle());
            ps.setString(3, recipe.getDesc());
            ps.setString(4, recipe.getImage());
            ps.setString(5, recipe.getCategory());
            ps.setString(6, recipe.getCuisine());
            
            ps.executeUpdate();
            
            try{
            
                if(ps.getGeneratedKeys().next()){
                    int recipeID = ps.getGeneratedKeys().getInt("r_id");
                    sql = "INSERT INTO rim (r_id, ingredient, amount, measure) VALUES (?, ?, ?, ?)";
                    ps = this.connection.prepareStatement(sql);
                    ps.setInt(1, recipeID);
                    ArrayList<IngredientBean> ings = recipe.getIngredients();
                    if(ings != null){
                        for (IngredientBean ing : ings) {
                            ps.setString(2, ing.getIngredient());
                            ps.setString(3, ing.getAmount());
                            ps.setString(4, ing.getMeasure());
                            ps.executeUpdate();
                        }            
                    }
                    
                    sql = "INSERT INTO steps (r_id, image, description) VALUES (?, ?, ?)";
                    ps = this.connection.prepareStatement(sql);
                    ps.setInt(1, recipeID);
                    ArrayList<StepBean> steps = recipe.getSteps();
                    if(steps != null){
                        for(StepBean step: steps){
                            ps.setString(2, step.getImage());
                            ps.setString(3, step.getDescription());
                            ps.executeUpdate();
                        }
                    }
                    return true;                
                } 
                
            } catch(SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public ArrayList<RecipePreviewBean> getRecipes(){
        
        ArrayList<RecipePreviewBean> recipes = new ArrayList<RecipePreviewBean>();
        
        try {
            
            String sql = "SELECT u.*, r.* FROM recipes r "
                    + "INNER JOIN users u ON r.u_id = u.u_id "
                    + "WHERE r.active = 1 ORDER BY r.r_date DESC"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){

                recipes.add(new RecipePreviewBean(rs.getString("r_id"), rs.getString("title"), rs.getString("description"), 
                        rs.getString("image"), rs.getString("name"), rs.getString("surname"), rs.getInt("likes")));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return recipes;
    }
    
    public ArrayList<RecipePreviewBean> searchRecipes(String key, String order, String category, String cuisine){
        
        ArrayList<RecipePreviewBean> recipes = new ArrayList<RecipePreviewBean>();
        
        try {
            
            String sql = "SELECT u.*, r.r_id, r.title, r.image, r.likes, LEFT(r.description, 100) AS description "
                    + "FROM recipes r "
                    + "INNER JOIN users u ON r.u_id = u.u_id "
                    + "WHERE r.active = 1 AND "
                    + "(LOWER(r.title) LIKE LOWER(?) OR LOWER(r.description) LIKE LOWER(?)) ";
            
            int i = 3;
            
            if(!category.equals("")){
                sql += "AND r.category = ? ";
            }
            
            if(!cuisine.equals("")){
                sql += "AND r.cuisine = ? ";
            }
            
            
            if(order.equals("likes")){
                sql += "ORDER BY likes DESC";
            } else {
                sql += "ORDER BY r_date DESC";
            }
            
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            
            if(!category.equals("")){
                ps.setString(i, category);
                i++;
            }
            
            if(!cuisine.equals("")){
                ps.setString(i, cuisine);
                i++;
            }
                        
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){

                recipes.add(new RecipePreviewBean(rs.getString("r_id"), rs.getString("title"), rs.getString("description"), 
                        rs.getString("image"), rs.getString("name"), rs.getString("surname"), rs.getInt("likes")));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return recipes;
        
    }
    
    
    public RecipeBean getRecipe(int id){
        
        RecipeBean recipe = new RecipeBean();
        
        try {
            
            String sql = "SELECT u.*, r.* FROM recipes r "
                    + "INNER JOIN users u ON r.u_id = u.u_id "
                    + "WHERE r.r_id = ? AND r.active = 1"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){

               recipe.setImage(rs.getString("image"));
               recipe.setTitle(rs.getString("title"));
               recipe.setDesc(rs.getString("description"));
               recipe.setUserID(rs.getInt("u_id"));
               recipe.setLikes(rs.getInt("likes"));
               recipe.setComments(rs.getInt("comments"));
            }
            
            sql = "SELECT * FROM rim WHERE r_id = ? AND active = 1";
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            ArrayList<IngredientBean> ings = new ArrayList<IngredientBean>();
            
            while(rs.next()){
                
                ings.add(new IngredientBean(rs.getString("ingredient"), rs.getString("amount"), rs.getString("measure")));
            }
            
            recipe.setIngredients(ings);
            
            sql = "SELECT * FROM steps WHERE r_id = ? AND active = 1";
            ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            ArrayList<StepBean> steps = new ArrayList<StepBean>();
            
            while(rs.next()){
                
                steps.add(new StepBean(rs.getString("image"), rs.getString("description")));
            }
            
            recipe.setSteps(steps);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return recipe;
        
    }
 
    public RecipeBean getRecipe(String id){
        return getRecipe(Integer.parseInt(id));
    }
    
    public int addLike(int uid, int rid){
        try {
            
            String sql = "INSERT INTO likes (r_id, u_id) VALUES (?, ?)"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, rid);
            ps.setInt(2, uid);
            
            if(ps.executeUpdate() > 0){
                
                sql = "UPDATE recipes SET likes = ("
                        + "SELECT COUNT(id) FROM likes WHERE r_id = ? AND active = 1) "
                        + "WHERE r_id = ?";
                ps = this.connection.prepareStatement(sql);
                ps.setInt(1, rid);
                ps.setInt(2, rid);
                
                if(ps.executeUpdate() > 0){
                    return getLikes(rid);
                }
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public int removeLike(int uid, int rid){
        try {
            
            String sql = "UPDATE likes SET active = 0 WHERE r_id = ? AND u_id = ?"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, rid);
            ps.setInt(2, uid);
            
            if(ps.executeUpdate() > 0){
                
                sql = "UPDATE recipes SET likes = ("
                        + "SELECT COUNT(id) FROM likes WHERE r_id = ? AND active = 1) "
                        + "WHERE r_id = ?";
                ps = this.connection.prepareStatement(sql);
                ps.setInt(1, rid);
                ps.setInt(2, rid);
                
                if(ps.executeUpdate() > 0){
                    return getLikes(rid);
                }
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public int getLikes(int id){
        try {
            
            String sql = "SELECT likes FROM recipes WHERE r_id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("likes");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public boolean doesUserLike(int uid, int rid){
        try {
            
            String sql = "SELECT id FROM likes WHERE r_id = ? AND u_id = ? AND active = 1";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, rid);
            ps.setInt(2, uid);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
        
    public boolean addComment(int rid, int uid, String text){
    	try{
    		String sql = "INSERT INTO comments (r_id,u_id,c_text) VALUES(?,?,?)";
    		PreparedStatement ps = this.connection.prepareStatement(sql);
    		ps.setInt(1, rid);
    		ps.setInt(2, uid);
    		ps.setString(3, text);
    		ps.executeUpdate();
    		return true;

    	}catch(SQLException ex){
    		Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    	}
    	return false;
    }
    
    public boolean addToFavor(int uid, int rid){
    	try{
    		String sql = "INSERT INTO favorites (u_id,r_id) VALUES(?,?)";
    		PreparedStatement ps = this.connection.prepareStatement(sql);
    		ps.setInt(1, uid);
    		ps.setInt(2, rid);
    		ps.executeUpdate();
    		return true;

    	}catch(SQLException ex){
    		Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    	}
    	return false;
    }

    public boolean removeFromFavor(int uid, int rid){
    	try{
    		String sql = "UPDATE favorites SET active = 0 WHERE u_id = ? AND r_id = ? AND active = 1";
    		PreparedStatement ps = this.connection.prepareStatement(sql);
    		ps.setInt(1, uid);
    		ps.setInt(2, rid);
    		ps.executeUpdate();
    		return true;

    	}catch(SQLException ex){
    		Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    	}
    	return false;
    }
    
    public boolean isInFav(int uid, int rid){
        try {
            
            String sql = "SELECT id FROM favorites WHERE r_id = ? AND u_id = ? AND active = 1";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, rid);
            ps.setInt(2, uid);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public ArrayList<RecipePreviewBean> getMyRecipes(int uid){
        
        ArrayList<RecipePreviewBean> recipes = new ArrayList<RecipePreviewBean>();
        
        try {
            
            String sql = "SELECT u.*, r.* FROM recipes r "
                    + "INNER JOIN users u ON r.u_id = u.u_id "
                    + "WHERE r.active = 1 AND u.u_id = ?";          
            
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, uid);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){

                recipes.add(new RecipePreviewBean(rs.getString("r_id"), rs.getString("title"), rs.getString("description"), 
                        rs.getString("image"), rs.getString("name"), rs.getString("surname"), rs.getInt("likes")));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return recipes;
    }

    public ArrayList<RecipePreviewBean> getFavor(int uid){
        
    	ArrayList<RecipePreviewBean> recipes = new ArrayList<RecipePreviewBean>();
        
        try {
            
            String sql = "SELECT u.*, r.* FROM favorites f "
                    + "INNER JOIN recipes r ON f.r_id = r.r_id "
                    + "INNER JOIN users u ON r.u_id = u.u_id "
                    + "WHERE r.active = 1 AND f.active = 1 AND f.u_id = ?";
            
            
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, uid);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){

                recipes.add(new RecipePreviewBean(rs.getString("r_id"), rs.getString("title"), rs.getString("description"), 
                        rs.getString("image"), rs.getString("name"), rs.getString("surname"), rs.getInt("likes")));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return recipes;
    }

    public ArrayList<CommentBean> getComments(int rid){
    	ArrayList<CommentBean> comments = new ArrayList<CommentBean>();
    	try{
            String sql = "SELECT * FROM comments WHERE r_id = ? AND active = 1";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){

                comments.add(new CommentBean(rs.getInt("id"), rs.getInt("u_id"), rs.getInt("r_id"), rs.getString("c_text")));
                
            }

    	}catch(SQLException ex){
    		Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    	}
    	return comments;
    }
    
    public ArrayList<CategoryBean> getCategories(){
        
        ArrayList<CategoryBean> data = new ArrayList<CategoryBean>();
        
        try {
            
            String sql = "SELECT * FROM categories WHERE active = 1"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                data.add(new CategoryBean(rs.getString("name"), rs.getString("image")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public ArrayList<RecipePreviewBean> getNewRecipes(){
        
        ArrayList<RecipePreviewBean> recipes = new ArrayList<RecipePreviewBean>();
        
        try {
            
            String sql = "SELECT u.*, r.r_id, r.title, r.image, r.likes, LEFT(r.description, 100) AS description "
                        + "FROM recipes r "
                        + "INNER JOIN users u ON r.u_id = u.u_id "
                        + "ORDER BY r.r_date DESC LIMIT 4"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                recipes.add(new RecipePreviewBean(rs.getString("r_id"), rs.getString("title"), rs.getString("description"), 
                        rs.getString("image"), rs.getString("name"), rs.getString("surname"), rs.getInt("likes")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return recipes;
        
    }
    
    public ArrayList<UserBean> getTopAuthors(){
        
        ArrayList<UserBean> users = new ArrayList<UserBean>();
        
        try {
            
            String sql = "SELECT u.u_id, u.name, u.surname, u.avatar, SUM(r.likes) AS rating FROM users u "
                        + "INNER JOIN recipes r ON u.u_id = r.u_id "
                        + "GROUP BY u.u_id, u.name, u.surname, u.avatar "
                        + "ORDER BY rating DESC LIMIT 4"; 
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                users.add(new UserBean(rs.getInt("u_id"), "", "", rs.getString("name"), rs.getString("surname"), rs.getString("avatar")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    }
    
}
