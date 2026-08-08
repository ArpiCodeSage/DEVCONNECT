package com.ari.devconnect.dto;

public class LoginRequest {
    private String usernameOrEmail;
    private String password;
    public LoginRequest()
    {}//for incoming JSON from React
    public LoginRequest(String usernameOrEmail,String password)
    {
        this.usernameOrEmail=usernameOrEmail;
        this.password=password;


    }
    public String getUsernameOrEmail(){
        return usernameOrEmail;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setUsernameOrEmail(String usernameOrEmail){
        this.usernameOrEmail=usernameOrEmail;
    }
}
