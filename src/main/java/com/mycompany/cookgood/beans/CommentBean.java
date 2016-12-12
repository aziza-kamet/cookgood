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
public class CommentBean {
 
    private int commentID;
    private int userID;
    private int recipeID;
    private String text;

    public CommentBean() {
    }

    public CommentBean(int commentID, int userID, int recipeID, String text) {
        this.commentID = commentID;
        this.userID = userID;
        this.recipeID = recipeID;
        this.text = text;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }   
        
}
