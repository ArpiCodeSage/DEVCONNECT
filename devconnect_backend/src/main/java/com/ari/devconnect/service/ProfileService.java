//it is the Backend Manager that prepares the deilvery box(ProfileResponse.java)
package com.ari.devconnect.service;

import com.ari.devconnect.dto.ProfileResponse;
import com.ari.devconnect.dto.ProfileUpdateRequest;
import com.ari.devconnect.model.User;
import com.ari.devconnect.model.Profile;
import com.ari.devconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileResponse getProfileByUsername(String username)//Finds a user by username in PostgreSQL and returns their profile details mapped to a ProfileResponse DTO
    {
//         Where does username come from?

// When someone in React (or Postman) types http://localhost:8080/api/profiles/ari_dev, the URL path contains "ari_dev".
// That string "ari_dev" gets passed into getProfileByUsername("ari_dev")
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found with username:" + username));
        Profile profile = user.getProfile();
        return new ProfileResponse(//to be sent back to React as JSON
                user.getUsername(),
                user.getEmail(),
                profile != null ? profile.getHeadline() : null,
                profile != null ? profile.getBio() : null,
                profile != null ? profile.getSkills() : null,
                profile != null ? profile.getLinkedinUrl() : null,
                profile != null ? profile.getGithubUrl() : null,
                profile != null ? profile.getWebsiteUrl() : null,
                profile != null ? profile.getAvatarUrl() : null
        );
    }

    public ProfileResponse updateProfile(String username,ProfileUpdateRequest request)//update logged-in user's profile
    {
        User user=userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("user not found with username:"+username));
        Profile profile=user.getProfile();
        if(profile==null)  
        {
            profile=new Profile();
            profile.setUser(user);
            user.setProfile(profile);
        }
        profile.setHeadline(request.getHeadline());
         profile.setBio(request.getBio());
        profile.setSkills(request.getSkills());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setWebsiteUrl(request.getWebsiteUrl());
        profile.setAvatarUrl(request.getAvatarUrl());
        userRepository.save(user);
        return getProfileByUsername(username);


    }

    
}
