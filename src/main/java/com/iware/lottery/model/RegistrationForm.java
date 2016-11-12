package com.iware.lottery.model;

import com.iware.lottery.DTOUtils;
import com.iware.lottery.domain.User;

import java.io.Serializable;

/**
 * Created by johnma on 2016/11/2.
 */
public class RegistrationForm implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String name;

    private String email;

    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "User{" + "name=" +  name + ", email=" + email + ", password=" + password + "}";
    }

    public User toEntity(){
        return DTOUtils.map(this, User.class);
    }
}
