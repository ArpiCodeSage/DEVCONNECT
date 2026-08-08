// This file is the REST API Controller (The Front Door) for authentication. Handles user entry into the app.

// Its job is to expose HTTP web addresses (/api/auth/register and /api/auth/login) so that external apps (like our React frontend or Postman) can send HTTP POST requests to register new users or log in!
package com.ari.devconnect.controller;
import com.ari.devconnect.dto.AuthResponse;
import com.ari.devconnect.dto.LoginRequest;
import com.ari.devconnect.dto.RegisterRequest;// Imports our DTOs so the controller can accept incoming JSON data (RegisterRequest, LoginRequest) and return token data (AuthResponse).
import com.ari.devconnect.service.AuthService;//imports AuthService so the controller can delegate the actual registration and login business logic to the service layer.
import org.springframework.http.ResponseEntity;//imports Spring's ResponseEntity class, which wraps HTTP responses along with HTTP status codes (like 200 OK, 400 Bad Request, 401 Unauthorized).
import org.springframework.web.bind.annotation.*;//Imports Web Annotations (@RestController, @RequestMapping, @PostMapping, @RequestBody).

@RestController// Tells Spring Boot: "This class handles RESTful HTTP requests and automatically converts return values into JSON format."
@RequestMapping("/api/auth")//Sets the base URL path for all endpoints in this file. All routes here will start with http://localhost:8080/api/auth.
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;//Automatically injects the AuthService bean into this controller when Spring Boot starts up.
    }
    @PostMapping("/register")//Listens for HTTP POST requests at the path /api/auth/register.
    public ResponseEntity<String> register(@RequestBody RegisterRequest request)//@RequestBody: Tells Spring Boot: "Take the raw JSON string coming from React/Postman body and convert it into a Java RegisterRequest object."
    {
        String responseMessage = authService.register(request);
        return ResponseEntity.ok(responseMessage);

    }
    //Calls authService.register(). If successful, returns a 200 OK HTTP status along with the success message string "User registered successfully!".
        @PostMapping("/login")// Listens for HTTP POST requests at the path /api/auth/login.
      public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
         System.out.println("RECEIVED LOGIN REQUEST FOR: " + request.getUsernameOrEmail());
        AuthResponse response=authService.login(request);
         System.out.println("SENDING TOKEN BACK: " + response.getToken()); 
        return ResponseEntity.ok(response);
      }




} 
// AuthController is the API Front Door / Endpoint Listener.

// Its ONLY role is:

// Listen for HTTP web requests at /api/auth/register and /api/auth/login.
// Parse JSON coming from Postman/React into DTOs (RegisterRequest, LoginRequest).
// Call AuthService to do the real work.
// Send back the HTTP response (200 OK or 401 Error) with the JSON data.