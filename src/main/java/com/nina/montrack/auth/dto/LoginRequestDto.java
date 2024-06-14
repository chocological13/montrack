package com.nina.montrack.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

  @NotBlank(message = "Please enter username")
  private String username;

  @NotBlank(message = "Please enter password")
  private String password;

}
