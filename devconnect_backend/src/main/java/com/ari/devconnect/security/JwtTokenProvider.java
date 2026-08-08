// this is the "engine" of authentication system.
// it has 3 main jobs:
//  The Event Wristband Analogy
// Imagine entering a concert / festival:
// generateToken() = Giving you a VIP Wristband 🎟️
// What it does: When you type your correct password at login, Spring Boot prints a unique, stamped wristband (JWT string) and hands it to you.
// What's inside: Your username and an expiration date (7 days).
// getUsernameFromJWT() = Reading the name on your Wristband 🔍
// What it does: When React makes a request later, Spring Boot looks at the wristband, reads your name off it, and knows: "Ah, this is Ari's request!"
// validateToken() = Checking if the Wristband is Fake or Expired 🛑
// What it does: The security guard at the gate checks 3 things:
// Is the wristband torn/tampered with?
// Is the stamp fake?
// Is it expired (older than 7 days)?
// If it passes, it returns true (come in!). If not, false (access denied!).
package com.ari.devconnect.security;

import java.security.Key;// 1. Represents a secret encryption key in Java memory.
import java.util.Date;// 2. Holds date & time (used to set when token expires).

import org.springframework.security.core.Authentication;// 3. Contains details of the logged-in user (username, password status).
import org.springframework.stereotype.Component;// 4. Tells Spring Boot to create and manage this class automatically.

import io.jsonwebtoken.Claims;// 5. The dictionary box inside a token that holds data (like username).
import io.jsonwebtoken.JwtException;// 6. The error thrown when a token is fake, broken, or expired.
import io.jsonwebtoken.Jwts;// 7. The main builder/reader tool for creating and opening tokens.
import io.jsonwebtoken.SignatureAlgorithm;// 8. The encryption algorithm choice (HS256).
import io.jsonwebtoken.security.Keys;// 9. Helper tool that turns your secret text string into a Key object.

@Component//tells SB: create and manage this object automatically so I can use it anywhere in my app.

public class JwtTokenProvider {// job: create,read tokens and check if they're valid

    private final String jwtSecret = "ecretKeyForDevConnectAppWhichIsSuperLongAndSecret12345";//only known by the SB backend server(the user/frontend never sees this). main job is to act
    //as the server's private stamp.
    //your serves uses this to sign tokens so that if a hacker in their browser tries to manually edit a token(eg: change username), the digital signature won't match and your
    //server will immediately reject it. how will the server identify it? 
    //by using signature of the JWT token which is : PAYLOAD+ jwtSecret

    private final int jwtExpirationInMs = 604800000;//7 days

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());//returns a key object
        //hmacShaKeyFor: hash-based message authentication code using sha encryption
        //getSigningKey(): Converts your secret text string into a cryptographic digital stamp key.
        //.getBytes() converts your string of text letters into an array of raw byte numbers (byte[]) 
        // so the Keys.hmacShaKeyFor(...) encryption function can process it!
    }

    public String generateToken(Authentication authentication) {//called right after a user logs in succesfully
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        return Jwts.builder() //builds and locks the JWT token string
                .setSubject(username)//put username inside token
                .setIssuedAt(new Date())//put start time inside token
                .setExpiration(expireDate)//put expiration data inside token
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)//lock token with secret key
                .compact();//turn into a single string
    }

    public String getUsernameFromJWT(String token) {//called when react sends a token back to the server in future API requests.
        //unlocks the token string and pulls out the username saved inside it
        Claims claims = Jwts.parserBuilder() //Declares a public method that receives a JWT token string (from React) and promises to return a String (the username).
                //claims:is a dictionary/map object inside JWT that holds the saved data (like username, expiration date
                .setSigningKey(getSigningKey())//Hands the parser your server's secret master key. If the token was signed with this exact key, the parser can safely unlock it.
                .build()//creates the parser instance
                .parseClaimsJws(token)//decrypts and checks the token string
                .getBody();//extracts the payload body
        return claims.getSubject();//fetches the main identifier stored inside the token(which we stores as the username in function 1) and returns it.
    }
    //If we didn't have this function, Spring Boot would know the request is valid, but it wouldn't know WHICH developer is posting the project!

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;

        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }//It is not executed when someone submits their username and password to log in.
    }

}
// getSigningKey(): Provides the server's private 256-bit encryption key.(the unique key inserted into the keyhole)
// SignatureAlgorithm.HS256: The specific industry-standard hashing 
// algorithm (HMAC using SHA-256) used to lock the token.(the type of lock mechanism built into the safe door.)

//TOKEN: a digitally signed,stateless credential string sent from the backend to the frontend after succesful authentication.
//it consists of header, payload and signature.
//header: tells the component what algorithm was used(eg. HS256)
//payload: plain readable info about the user
//signature: a mathematical hash: header+ payload and running this through the server's secret code
//parsing : means taking a long string of text and breaking it down to extract readable data.
//parseBuilder() : In the JJWT library, parserBuilder() is a factory tool that constructs a customized JWT parser(basically a TokenReader).
//Why it's called a "Builder":
// In software design, the Builder Pattern allows you to configure(set-up or give instructions to) a complex object step-by-step (adding the signing key, setting allowed clock skew, etc.) before calling .build() to create the final parser!

//build: creates a parser tool object.Pressing the green START button on the machine.
//The Create Switch. It takes all your attached options and creates the final ready-to-use Token Reader.



//parseBuilder: The Factory / Blueprint (the machinery that makes things).
//The Setup Tool. It lets you attach options (like your secret key) before reading a token.



// 1. getSigningKey() (Your Helper Function) 🛠️
// It RETURNS a value.
// It is a helper method written by YOU in JwtTokenProvider.java that takes your secret text ("ari@jsr2004...") and returns a Java Key object.


// 2. setSigningKey(...) (JJWT Library Function) 📥
// It ACCEPTS a value.
// It is a built-in method inside the JJWT Library Parser that accepts the Key object you pass into it, so the parser knows which key to use for unlocking tokens.





// Jwts.parserBuilder() builds the JwtParser tool.
// JwtParser's job is to:

// Break down the token string into its 3 parts: Header.Payload.Signature.
// Re-calculate the signature using your jwtSecret.
// Extract the Payload data (like username and expiration date)!