/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cookgood.beans;

import java.util.ArrayList;

/**
 *
 * @author Aziza
 */
public class RecipeBean {
    
    private int userID;
    private String title;
    private String desc;
    private String image;
    private ArrayList<IngredientBean> ingredients;
    private ArrayList<StepBean> steps;
    private String category;
    private String cuisine;
    private int likes;
    private int comments;

    public RecipeBean() {
        this.image = "recipe.jpg";
    }

    public RecipeBean(int userID, String title, String desc, String image, ArrayList<IngredientBean> ingredients, 
            ArrayList<StepBean> steps, String category, String cuisine, int likes, int comments) {
        this.userID = userID;
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
        this.category = category;
        this.cuisine = cuisine;
        this.likes = likes;
        this.comments = comments;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<IngredientBean> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientBean> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<StepBean> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepBean> steps) {
        this.steps = steps;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }  

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    
    
}
