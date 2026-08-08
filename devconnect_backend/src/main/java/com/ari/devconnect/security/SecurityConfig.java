// This file is the Master Security Controller (The Bouncer) of your application. It sets up 3 major rules:
// Tells Spring Boot to encrypt passwords using BCrypt.
// Enables the Authentication Manager to verify user credentials during login.
// Sets up Route Access Rules: Makes /api/auth/** public (so anyone can register/login), while locking down all other endpoints so only users with valid JWT tokens can enter!
package com.ari.devconnect.security;

import org.springframework.context.annotation.Bean;// Tells Spring Boot: "Create this object and manage it in memory so any class can use it via @Autowired."
import org.springframework.context.annotation.Configuration;//marks this class as a master setttings/configuration class in SpringBoot
import org.springframework.security.authentication.AuthenticationManager;// Spring Security's main engine that verifies passwords during user login.
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;//Spring Boot's internal helper to fetch the default authentication configuration.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;//The main configuration object used to build security rules for web traffic (like setting public/private URLs).
import org.springframework.security.config.http.SessionCreationPolicy;//Lets us specify whether Spring Security should create traditional HTTP session cookies or stay stateless (JWT mode).
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;//he industry-standard hashing algorithm used to securely encrypt user passwords before saving to PostgreSQL.
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;//he chain of security filters that every web request passes through when hitting your server.
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration//declares SecurityConfig as a Spring configuration class
public class SecurityConfig {
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    public SecurityConfig(JwtTokenProvider tokenProvider,CustomUserDetailsService customUserDetailsService)
    {
        this.tokenProvider=tokenProvider;
        this.customUserDetailsService=customUserDetailsService;
    }

    @Bean 
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new  JwtAuthenticationFilter(tokenProvider,customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//Creates a BCryptPasswordEncoder bean. Whenever a user registers, we use this to convert "myPassword123" into an un-hackable hash string like "$2a$10$e7...".

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    //Exposes Spring's built-in AuthenticationManager as a bean so our AuthService can call it during user login to check credentials

    //defines the master security rules for incoming HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())//Disables Cross-Site Request Forgery cookies. We don't need CSRF cookies because JWT tokens are stateless.
                .cors(cors->cors.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//Tells Spring Boot: "Never store user sessions in memory on the server. Every request must be verified independently with a JWT token."Scalability! If you have 100,000 active users, storing 100,000 sessions in server RAM will crash your server or slow it down.
                // With stateless JWTs, 1,000,000 users can use your app without using 1 single byte of server memory!
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()//Makes all auth URLs (Web addresses used for signing up or logging in: like /api/auth/register and /api/auth/login) PUBLIC. Anyone can access them without a token! otherwise new users aka w/o any token would never be able to log in
                .anyRequest().authenticated());//Makes EVERY OTHER ENDPOINT (like /api/projects, /api/profiles) PRIVATE. Access requires a valid JWT token!
        
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
                return http.build();//Finalizes and builds the security configuration chain.

    }

    

}

// When you log into a website like Amazon or Facebook, their server sends a small text file (cookie) to Chrome: session_id=abc123xyz.
// Every time you click a page on Amazon, Chrome automatically sends that text file along with your click so Amazon remembers who you are.
// That small text file saved in your browser is called a Cookie!
// 🍪 The Bank Cookie Story (How CSRF Works)
// Imagine you log into your bank website (mybank.com):
// Old-school Banking Sites: When you log in, mybank.com saves a session cookie in your browser that says: "This browser belongs to Ari".
// Browsers have a built-in rule: Whenever any request goes to mybank.com, automatically attach Ari's session cookie!
// 😈 The CSRF Attack
// Now, while still logged into your bank, you open a new tab and visit a sketchy website (evil-games.com):
// evil-games.com has a hidden button or script that automatically sends a hidden request to mybank.com/transfer?to=hacker&amount=1000.
// Your browser sees the destination is mybank.com, so it automatically attaches your stored bank cookie!
// mybank.com receives the request, sees your valid cookie, and thinks YOU clicked "Transfer $1,000"!
// That trick is called Cross-Site Request Forgery (CSRF).
//When Chrome visits a website, Chrome automatically attaches Cookies to itself. But Chrome does NOT automatically attach JWT tokens to itself. It ignores JWT tokens unless your code specifically attaches them.
//Because Chrome won't attach the JWT token automatically, your React code has to explicitly write code to attach it: header.add("Authorization", token) Only YOUR code can do this action.
//ALSO
//Browsers have a security wall called Same-Origin Policy. Tab A (evil-games.com) is physically blocked by Chrome from looking inside the memory or variables of Tab B (devconnect.com).
// Because evil-games.com cannot look inside devconnect.com's memory, it cannot grab your JWT token!
//Chrome does NOT attach JWT tokens automatically.
// For the token to be included, evil-games.com would have to READ the token out of your React memory first—which is BLOCKED by the Same-Origin Policy!



//BEAN:
// In standard Java, whenever you want to use an object, you create it manually using new: MyService service = new MyService();
// In Spring Boot, Spring manages objects for you automatically.
// A Spring Bean is simply a Java object that is created, configured, and managed by Spring Boot's container.
// 💡 The Coffee Shop Analogy:
// Standard Java: You buy coffee beans, roast them yourself, grind them yourself, and brew the coffee manually.
// Spring Bean: You press a button at an automatic espresso machine (@Bean), and Spring Boot prepares the coffee, holds it in memory, and hands it to whoever asks for it!
    



// How Spring Security blocks users behind the scenes:

// When SecurityConfig builds http.build(), Spring Security creates an internal Filter Chain (a series of 15+ built-in security guards) that wraps around your server.

// When an HTTP request hits a private URL (like GET /api/profiles/me):
//  Spring Security's FilterSecurityInterceptor guard checks the request.
// Because .anyRequest().authenticated() was set in SecurityConfig, it checks if the request has a valid login proof.(this proof is obtained from jwtauthfilter.java)
// If NO proof is present: Spring Security immediately stops the request and returns HTTP 401 Unauthorized (Access Denied) before your controller code is even touched!





// Spring Security has a default filter called UsernamePasswordAuthenticationFilter that expects traditional session login forms.

// Because we use stateless JWT tokens:

// When an HTTP request comes in with a JWT token header (Authorization: Bearer <token>), we want our JwtAuthenticationFilter to intercept and verify the token FIRST.
// If our JwtAuthenticationFilter verifies the token, it sets the proof in SecurityContextHolder.
// Now, when the request reaches UsernamePasswordAuthenticationFilter and .anyRequest().authenticated(), Spring Security sees the proof in memory and immediately grants access!
// If we didn't add .addFilterBefore(...), Spring Security wouldn't know when to execute our custom JWT filter, and requests to private endpoints would keep failing with 401 Unauthorized.
// If you don't write http.addFilterBefore(...):

// Guard A (your JWT filter) is standing in the cafeteria doing nothing because nobody told him where his post is in the line.
// A passenger walks up to Guard B.
// Guard B looks at the passenger's hand: No stamp! (because Guard A never checked them).
// Guard B kicks the passenger out: "Access Denied (401 Unauthorized)!"