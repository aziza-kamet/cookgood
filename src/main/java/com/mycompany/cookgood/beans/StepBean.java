/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cookgood.beans;

import java.io.Serializable;

/**
 *
 * @author Aziza
 */
public class StepBean implements Serializable{
    
    private String image;
    private String description;

    public StepBean() {
        this.image = "";
        this.description = "";
    }

    public StepBean(String image, String description) {
        this.image = image;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
