package com.ari.devconnect.model;

import java.time.LocalDateTime;

// import org.springframework.context.annotation.Profile;//sb's internal tool

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


// JPA:JPA stands for Java Persistence API (also known as Jakarta Persistence). It is a standard Java specification used for managing relational data and object-relational mapping in applications.

@Entity
//treat this class as a database table blueprint
@Table(name = "users")
//specifies that the table in postgresql should be named users(instead of default user)
public class User {

    @Id
    //marks id as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //tells postgresql to automatically increment the ID whenever a new user is saved.
    private Long id;
    //holds user's ID no. in Java
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    @Column(nullable = false)
    private String role = "USER_ROLE";
    //sets a default user role
    private LocalDateTime createdAt = LocalDateTime.now();
    //automatically stores the timestamp of when the object was created in Java
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
    //{
//      Defines a 1-to-1 relationship (1 User has 1 Profile).
// mappedBy = "user": Tells Hibernate: "The Profile entity owns the Foreign Key relationship via its user field."
// cascade = CascadeType.ALL: If you delete or save a User, Hibernate automatically deletes or saves their associated Profile too!
//     //}


    public User() {
//         Imagine you are rebuilding a LEGO model using instructions:

// First, you take a blank baseplate (an empty object).
// Then, step by step, you place each brick onto it (id, username, email).
// Hibernate (JPA) works the exact same way when reading data from PostgreSQL!
// When PostgreSQL returns a row of data (e.g., id: 1, username: "ari", email: "ari@test.com"), Hibernate needs to turn that database row into a Java User object:
// It calls new User() (the empty constructor) to create a blank User instance in Java memory.
// Then, it fills in the fields one by one (id, username, email).
// If you don't provide an empty constructor public User() {}, Hibernate cannot create the initial blank object and will throw an error when fetching data!
    }
    

    // JPA requires this so Hibernate can recreate objects when reading rows from the database.
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setPassword() {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = createdAt;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
//    Making fields private protects your data from being modified accidentally by other parts of the application.
// getUsername() (Getter) lets other classes read the username.
// setUsername(String username) (Setter) lets other classes update the username safely.
//here's what safely means:
// If a field is public, ANY code in your app can set it to invalid, dangerous, or corrupt data without warning:
// When you use a Setter (setUsername), you can add validation rules inside the setter before assigning the value:
// That is what "safely" means—the class guards its own data!
// This Object-Oriented Programming (OOP) concept is called Encapsulation.

}
