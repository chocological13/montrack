package com.nina.montrack.user.entity.dto;

import com.nina.montrack.user.entity.Users;
import lombok.Data;

@Data
public class UserProfileDTO {

  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String bio;
  private String avatar;

  public UserProfileDTO userToUserProfileDTO(Users user) {
    UserProfileDTO userProfile = new UserProfileDTO();
    userProfile.setUsername(user.getUsername());
    userProfile.setEmail(user.getEmail());
    userProfile.setFirstName(user.getFirstName());
    userProfile.setLastName(user.getLastName());
    userProfile.setBio(user.getBio());
    userProfile.setAvatar(user.getAvatar());
    return userProfile;
  }

  public Users userProfileToUsers(Users user, UserProfileDTO userProfileDTO) {
    if (userProfileDTO.username != null) {
      user.setUsername(userProfileDTO.username);
    }
    if (userProfileDTO.email != null) {
      user.setEmail(userProfileDTO.email);
    }
    if (userProfileDTO.firstName != null) {
      user.setFirstName(userProfileDTO.firstName);
    }
    if (userProfileDTO.lastName != null) {
      user.setLastName(userProfileDTO.lastName);
    }
    if (userProfileDTO.bio != null) {
      user.setBio(userProfileDTO.bio);
    }
    if (userProfileDTO.avatar != null) {
      user.setAvatar(userProfileDTO.avatar);
    }
    return user;
  }
}
