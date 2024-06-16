package com.nina.montrack.auth.dto.register;

import com.nina.montrack.user.entity.Users;
import lombok.Data;

@Data
public class RegisterRequestDto {

  private String username;
  private String email;
  private String password;

  public Users toEntity() {
    Users user = new Users();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }
}
