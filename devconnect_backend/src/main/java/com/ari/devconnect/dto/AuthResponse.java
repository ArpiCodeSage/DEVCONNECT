package com.ari.devconnect.dto;

public class AuthResponse {
    private String token;
    private String tokenType="Bearer";
    public AuthResponse(){

    }
    public AuthResponse(String token)
    {
        this.token=token;
    }
    public String getToken(){
          return token;
    }
    public String getTokenType(){
        return tokenType;
    }
    public void setToken(String token)
    {
        this.token=token;
    }
    public void setTokenType(String tokenType){
        this.tokenType=tokenType;
    }

    
}
//  why we need both setter function and setting using Constructor here:
//  User (React Frontend): Needs the token after logging in so it can save it (in localStorage). Every time the user navigates pages or posts a project, React sends this token in the request header (Authorization: Bearer <token>).
// Spring Boot (Backend): Reads the token on every incoming request to answer: "Who is making this request? Is this token valid? Is this user allowed to edit this profile?"


// AuthResponse is the Response Wrapper DTO.

// When a user logs in successfully, you don't just send a raw string back to React. You wrap it inside AuthResponse:

// json
// {
//   "token": "eyJhbGciOiJIUzI1NiJ9...",
//   "tokenType": "Bearer"
// }
// This gives React a clean, structured JSON object containing both the token string and the tokenType (Bearer).