package com.frew.crew.auth;

import com.frew.crew.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private UUID identifier;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
  private String token;
}
