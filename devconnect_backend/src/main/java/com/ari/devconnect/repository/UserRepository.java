//Defines what columns exist in PostgreSQL without writing SQL.


package com.ari.devconnect.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ari.devconnect.model.User;
public interface UserRepository extends JpaRepository<User, Long>
// Declares UserRepository as an interface.
// extends JpaRepository<User, Long>: Tells Spring: "Manage the User entity table, where the Primary Key (id) is of type Long." This instantly unlocks methods like .save(), .findById(), and .deleteById().
//"My repository should inherit all these methods, but for the User entity whose ID is a Long."
{    
    Optional<User> findByUsername(String username);
    //  Spring parses the method name findByUsername and automatically executes this SQL query in PostgreSQL: SELECT * FROM users WHERE username = ?
//  Optional is a protective wrapper box around an object. It forces you to check if the user exists before accessing it.
   Optional<User> findByEmail(String email);
   Boolean existsByUsername(String username);
   Boolean existsByEmail(String email);

   
//we don't need a profile repo cuz we have onetoone b/w profile and java
//JpaRepository is a pre-built interface in Spring Data JPA that provides ready-to-use methods 
// for database tasks, meaning saving, finding, updating, and deleting records without writing SQL.
}
// In Java:

// A class contains both data and the actual implementation code (how things are done).
// An interface is a contract or blueprint that lists method names without writing their code.
// 💡 Real World Analogy:
// Think of a wall socket plug:

// The wall socket is an interface. It specifies the standard shape and voltage contract.
// Anything plugged in (laptop charger, TV) is the implementation that executes the work.
// Why we use an interface for Repositories:
// You only define the rules.



// public interface UserRepository extends JpaRepository<User, Long> {
//     Optional<User> findByUsername(String username);
// }
// You don't write any class implementation or SQL queries! Spring Boot automatically creates a hidden concrete class behind the scenes that fulfills the contract and executes the SQL queries for you.

//.................................................................................................................
//When WOULD we need a ProfileRepository?
// If we want to fetch, search, or update profiles directly without loading the User object first—for example:

// Searching developers by skills (findBySkillsContaining("React"))
// Updating profile URLs directly
