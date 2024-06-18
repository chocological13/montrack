package com.nina.montrack.auth.entity.dto.forgorPassword;

import lombok.Data;

@Data
public class ForgotPasswordResponseDTO {
  private String message;
  private String resetTokenURL;
}
