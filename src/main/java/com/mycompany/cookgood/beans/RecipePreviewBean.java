/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cookgood.beans;

/**
 *
 * @author Aziza
 */
public class RecipePreviewBean extends RecipeBean{
    
    private String recipeID;
    private String name;
    private String surname;
    private int likes;

    public RecipePreviewBean() {
    }    
    
    public RecipePreviewBean(String recipeID, String title, String description, 
            String image, String name, String surname, int likes) {
        setTitle(title);
        setDesc(description);
        setImage(image);
        this.recipeID = recipeID;
        this.name = name;
        this.surname = surname;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getTitle(){        
        return super.getTitle();        
    }

    public String getImage(){
        return super.getImage();
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    } 

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }   
        
}
