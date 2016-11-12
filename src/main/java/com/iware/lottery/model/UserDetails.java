package com.iware.lottery.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by johnma on 2016/11/2.
 */
public class UserDetails implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String email;

    private String password;

    private String salt;

    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString(){
        return  "UserDetails{" + "id=" + id +", name=" + name + ", email=" + email + ", password=" + password +
                ", salt=" + salt + ", createdDate = " + createdDate + "}";
    }
}
