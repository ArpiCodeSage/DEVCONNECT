//In Spring Boot architecture:

// Controller: Receives HTTP requests from React.
// Repository: Talks directly to PostgreSQL DB.
// AuthService (Service Layer): The brain in the middle! It holds the business logic for registering users (hashing passwords with BCrypt) and logging users in (verifying passwords and triggering JWT generation).
package com.ari.devconnect.service;
import com.ari.devconnect.dto.AuthResponse;
import com.ari.devconnect.dto.LoginRequest;
import com.ari.devconnect.dto.RegisterRequest;//imports out DTO's used to receive user input from React and return token responses.
import com.ari.devconnect.model.Profile;
import com.ari.devconnect.model.User;//so that we can create new User and Profile objects
import com.ari.devconnect.repository.UserRepository;//so that we can query PostgreSQL and save new users
import com.ari.devconnect.security.JwtTokenProvider;//to generate Jwt tokens after login
import org.springframework.security.authentication.AuthenticationManager;//Spring Security's engine to verify login credentials.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;// Unauthenticated wrapper object containing typed username/password.
import org.springframework.security.core.Authentication;//Authenticated result object returned after successful login.
import org.springframework.security.crypto.password.PasswordEncoder;// Imports BCrypt encoder interface to hash passwords.
import org.springframework.stereotype.Service;//Marks this class as a Spring Service (business logic layer).


@Service//Marks AuthService as a Spring-managed service bean.
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtTokenProvider tokenProvider)
    {
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.tokenProvider=tokenProvider;

    }//Spring Boot automatically injects all required beans (UserRepository, PasswordEncoder, etc.) into AuthService when starting up.
     
    public String register(RegisterRequest request)
    {
        if(userRepository.existsByUsername(request.getUsername()))
        {
            throw new RuntimeException("Username is already taken!");
        }
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email is already registered!");
        }
        User user=new User(request.getUsername(),request.getEmail(),passwordEncoder.encode(request.getPassword()));
        Profile profile=new Profile();
        profile.setUser(user);
        user.setProfile(profile);//linking bidirectionally
        userRepository.save(user);//Saves the User (and cascading Profile) to PostgreSQL and returns a success message string.
        return "User registered successfully!";
        
    }
       public AuthResponse login(LoginRequest request) {
        try {
            System.out.println("ATTEMPTING LOGIN FOR USER: " + request.getUsernameOrEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail(),
                            request.getPassword()
                    )
            );

            System.out.println("AUTHENTICATION SUCCESSFUL!");

            String token = tokenProvider.generateToken(authentication);
            return new AuthResponse(token);

        } catch (Exception e) {
            System.out.println("LOGIN ERROR OCCURRED: " + e.getMessage());
            e.printStackTrace(); // 🟢 PRINTS THE EXACT STACK TRACE!
            throw e;
        }
    }

    
}
