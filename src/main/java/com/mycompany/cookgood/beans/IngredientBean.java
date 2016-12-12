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
public class IngredientBean implements Serializable{
    
    private String ingredient;
    private String amount;
    private String measure;

    public IngredientBean() {        
    }

    public IngredientBean(String ingredient, String amount, String measure) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }    
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    
}
