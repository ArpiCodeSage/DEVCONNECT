package com.ari.devconnect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
// Postman ➔ AuthController ➔ AuthService ➔ PasswordEncoder (BCrypt) ➔ UserRepository ➔ PostgreSQL Database!