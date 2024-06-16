package com.nina.montrack.user.entity.dto;

import com.nina.montrack.user.entity.Users;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

  @NotNull
  private String username;
  @NotNull
  private String email;
  @NotNull
  private String password;

  public Users toEntity() {
    Users user = new Users();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }
}
