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
public class UserBean implements Serializable{
    
    private int id;
    private String login;
    private String pwd;
    private String name;
    private String surname;
    private String avatar;

    public UserBean() {
        this.id = -1;
        this.login = "";
        this.pwd = "";
        this.name = "None";
        this.surname = "None";
        this.avatar = "user.jpg";
    }

    public UserBean(int id, String login, String pwd, String name, String surname, String avatar) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.pwd = pwd;
        this.surname = surname;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }  
        
}
