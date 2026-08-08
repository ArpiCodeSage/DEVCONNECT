// Spring Security needs a way to fetch a user's hashed password and roles from PostgreSQL when someone logs in or presents a JWT token. This class connects Spring Security to our UserRepository.
package com.ari.devconnect.security;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.UserRepository;//Imports our database User entity and UserRepository so we can query PostgreSQL.
import org.springframework.security.core.authority.SimpleGrantedAuthority;// Imports Spring Security's authority class, which wraps a user role string (e.g. "ROLE_USER") into a format Spring Security understands.
import org.springframework.security.core.userdetails.*;// Imports core Spring Security user interfaces (UserDetailsService, UserDetails, UsernameNotFoundException).
import org.springframework.stereotype.Service;//Marks this class as a Spring Service bean.
import java.util.Collections;

@Service//Marks the Class Purpose: It tells human developers and Spring Boot: "This class holds core business logic (Service Layer).Creates a Managed Bean: It tells Spring Boot: "Automatically create, configure, and manage ONE instance of this object( Spring Boot creates a single shared copy (Singleton) of that class in memory and reuses that same copy everywhere throughout your app) in memory when the application starts!"
public class CustomUserDetailsService implements UserDetailsService { 
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }
    @Override// "Hey Java! I am intentionally replacing/fulfilling a method (loadUserByUsername) that was defined in a parent class or interface (UserDetailsService). Please double-check that I spelled the method name and parameters 100% correctly!"
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
        User user=userRepository.findByUsername(usernameOrEmail)
        .orElseGet(()->userRepository.findByEmail(usernameOrEmail)
        .orElseThrow(()->new UsernameNotFoundException("user not found"+usernameOrEmail)));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority(user.getRole()))//Converts OUR database User object into Spring Security's official internal User object
        );
    } 
    



    
}
