package com.frew.crew.user;

import com.frew.crew.card.Card;
import com.frew.crew.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
  private Card card; // Reference to CardDTO instead of full Card entity
}
