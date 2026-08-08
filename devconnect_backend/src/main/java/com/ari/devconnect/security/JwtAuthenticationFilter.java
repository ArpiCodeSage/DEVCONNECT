// JwtAuthenticationFilter.java is the missing bridge that extracts the JWT token from the HTTP header and hands the valid login proof over to SecurityConfig!
package com.ari.devconnect.security;

import jakarta.servlet.FilterChain;// Represents the list of security filters the HTTP request must travel through.
import jakarta.servlet.ServletException;//Exception thrown if a servlet error occurs during processing.
import jakarta.servlet.http.HttpServletRequest;//Object representing the incoming HTTP request from React (contains headers, URL, parameters).
import jakarta.servlet.http.HttpServletResponse;//Object representing the outgoing HTTP response sent back to React.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;//Spring Security's standard Authentication object holding a verified user's identity and authorities.
import org.springframework.security.core.context.SecurityContextHolder;// *The central security vault in Spring Boot.* Holds the Authentication object for the current thread/request.
import org.springframework.security.core.userdetails.UserDetails;// Core Spring Security interface containing user information (username, password, roles).
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;//Helper tool that attaches web-specific request details (like IP address and session ID) to the authentication object.
import org.springframework.util.StringUtils;//Helper utility to safely check if strings are null or empty (StringUtils.hasText()).
import org.springframework.web.filter.OncePerRequestFilter;//Abstract base class ensuring this security filter executes only once per request dispatch.
import java.io.IOException;//Exception thrown for I/O errors during request processing.

public class JwtAuthenticationFilter extends OncePerRequestFilter {//extends OncePerRequestFilter to become custom HTTP security filter.

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) { // Injects dependencies: JwtTokenProvider (to validate and parse tokens) and CustomUserDetailsService (to load user details from PostgreSQL).
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //The core method executed automatically for every incoming HTTP request.
        String token = getJwtFromRequest(request);//Calls our helper method to extract the raw JWT token string from the Authorization: Bearer <token> header.
        if (StringUtils.hasText(token) && tokenProvider.validateToken((token))) {//Checks if the token string exists (not null/empty) AND passes signature math & expiration verification via tokenProvider.validateToken(token).java
              String username=tokenProvider.getUsernameFromJWT(token);
              UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
              UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());//Creates Spring Security's official Authentication proof object containing userDetails and permissions.
              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authentication);//THE KEY MOMENT: Saves the verified authentication proof object inside SecurityContextHolder. Now SecurityConfig sees the user is logged in!
              filterChain.doFilter(request, response);
              
        }
    }
    private String getJwtFromRequest(HttpServletRequest request)
    { 
        String bearerToken=request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
        

    }
        @Override//OncePerRequestFilter (the base class we extended) has a built-in method called shouldNotFilter(). and Spring Security's internal framework engine calls it automatically beofre calling doFilterInternal
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/auth");
    }
}
// A Servlet is Java's low-level Web Server Listener.

// When a web request arrives at your Spring Boot server (e.g. from Chrome or React):
// Tomcat (Web Server) receives the raw TCP network bytes.
// Tomcat converts those bytes into a Java Servlet Request object (HttpServletRequest).
// The Servlet handles the request and sends back a Java Servlet Response object (HttpServletResponse).
// Every web request in Java (including Spring Boot controllers, filters, and APIs) is built on top of Servlets behind the scenes!


// 1. new WebAuthenticationDetailsSource()
// What it is: Creates a helper object from Spring Security designed to extract HTTP metadata from web requests.
// 2. .buildDetails(request)
// What it does: Reads the incoming request object (the HttpServletRequest) and extracts two specific pieces of web metadata:
// Remote IP Address: The IP address of the user making the request (e.g. 192.168.1.50).
// Session ID: The HTTP session ID (if one exists).
// It packages those 2 web details into a WebAuthenticationDetails object.



//  Spring Security has a chain of filters (Station 1, Station 2, Station 3...) that every HTTP request must walk through before it reaches your REST Controller (AuthController, ProfileController: API Receptionist/Front Counter of your backend server).

// java:
// filterChain.doFilter(request, response);
// JwtAuthenticationFilter is Station 1 (our custom ID check).
// Once our filter finishes checking the JWT token, calling filterChain.doFilter(request, response) is like the guard saying: "I am done checking your token. Step forward to the next filter in line!"  





//Purpose of getJwtfromRequest:

// When React sends a token in the HTTP header, the standard RFC 6750 specification mandates that the header value MUST look like this:

// Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

// Notice the text at the start: "Bearer " (which is 7 characters long: B-e-a-r-e-r-space).

// Why strip it?
// The JJWT token validator (Jwts.parserBuilder()) only accepts the pure token string itself (eyJhbGci...).

// If you don't strip off "Bearer ", JJWT tries to validate the letters "Bearer " as part of the cryptographic hash, which causes signature verification to fail!

// So .substring(7) strips off "Bearer " and hands the clean raw token string to the validator.