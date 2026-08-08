// This file is the REST API Controller (The Public/Private Front Counter) for developer portfolios.

// Its job is to expose 2 web endpoints:

// GET /api/profiles/{username} ➔ Lets anyone on the internet view a developer's portfolio profile.
// PUT /api/profiles/me ➔ Lets a logged-in developer edit their own profile (headline, bio, skills, GitHub link) using their JWT token.

package com.ari.devconnect.controller;
 import com.ari.devconnect.dto.ProfileResponse;//so that the controller can  send back profile data
 import com.ari.devconnect.dto.ProfileUpdateRequest;//receive updated fields
 import com.ari.devconnect.service.ProfileService;
 import org.springframework.http.ResponseEntity;//Meaning: Imports Spring's wrapper object used to return HTTP status codes (like 200 OK, 404 Not Found).
 import org.springframework.security.core.annotation.AuthenticationPrincipal;// Spring Security annotation that automatically injects the logged-in user's identity from the active JWT token into the method's parameter varibale userDetails(directly!).
 import org.springframework.security.core.userdetails.UserDetails;//The object holding the logged-in user's details (username, permissions).
 import org.springframework.web.bind.annotation.*;//Meaning: Imports Web Annotations (@RestController, @RequestMapping, @GetMapping, @PutMapping, @PathVariable, @RequestBody).



@RestController//Handles RESTful web requests and automatically converts Java return objects into JSON.
@RequestMapping("/api/profiles")//: Sets the base URL path for all endpoints in this file (http://localhost:8080/api/profiles).
public class ProfileController {
    private final ProfileService profileService;
    public ProfileController(ProfileService profileService)
    {
        this.profileService=profileService;
    }
    @GetMapping("{username}")//Listens for HTTP GET requests at /api/profiles/ari_dev.
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String username)//Extracts the text "ari_dev" directly from the URL path
    {
        ProfileResponse profile=profileService.getProfileByUsername(username);
        return ResponseEntity.ok(profile);
    }// returns a 200 OK HTTP status with the ProfileResponse JSON data!

    @PutMapping("/me")//Listens for HTTP PUT requests at /api/profiles/me.
    public ResponseEntity<ProfileResponse> updateMyProfile(
        @AuthenticationPrincipal UserDetails userDetails,// Spring Security inspects the incoming JWT token, extracts the logged-in user, and injects their userDetails directly into this variable!
        @RequestBody ProfileUpdateRequest request//Converts incoming JSON body (new bio, skills, links) into a ProfileUpdateRequest object.
    )
    {
        String username=userDetails.getUsername();
        ProfileResponse updatedProfile=profileService.updateProfile(username,request);
        return ResponseEntity.ok(updatedProfile);
    }
    
}




//WHAT IS REST?
//REST stands for Representational State Transfer.
//REST is an architectural standard/style for designing web APIs so that frontends (like React, iOS apps, Android apps) can talk to backends (Spring Boot) using standard HTTP Web Verbs.


//WHAT IS AN API?
// Imagine sitting at a restaurant:

// You (React Frontend / App): Want to get food from the kitchen, but you are not allowed to walk into the kitchen yourself.
// The Kitchen (PostgreSQL Database & Spring Boot): Holds all the raw food and ingredients.
// The Menu & Waiter (The API):
// The Menu lists what you can ask for (GET /api/profiles, POST /api/auth/login).
// The Waiter takes your order, carries it to the kitchen, and brings back your plate of food (JSON data).
// 💻 In Web Development:
// An API is a defined set of rules/urls that lets two different software applications talk to each other.

// React Frontend speaks JavaScript.
// Spring Boot Backend speaks Java.
// PostgreSQL speaks SQL.
// How do they communicate? Through APIs! React sends an HTTP request to your Spring Boot API URL (/api/profiles), and your Spring Boot API returns data in JSON format—a universal language that both Java and React understand!
//A Web API is simply an API that works over the Internet / HTTP protocol using Web URLs (like http://localhost:8080/api/profiles).
