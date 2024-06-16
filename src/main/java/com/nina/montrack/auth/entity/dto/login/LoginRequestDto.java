package com.nina.montrack.auth.entity.dto.login;

import com.nina.montrack.user.entity.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

  @NotBlank
  private String username;
  @NotBlank
  private String password;

  public Users toEntity() {
    Users user = new Users();
    user.setUsername(username);
    user.setPassword(password);
    return user;
  }

}
