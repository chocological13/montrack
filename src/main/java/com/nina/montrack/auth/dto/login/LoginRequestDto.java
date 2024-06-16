package com.nina.montrack.auth.dto.login;

import com.nina.montrack.user.entity.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

  @NotBlank(message = "Please enter username")
  private String username;

  @NotBlank(message = "Please enter password")
  private String password;

  public Users toEntity() {
    Users user = new Users();
    user.setUsername(username);
    user.setPassword(password);
    return user;
  }
}
