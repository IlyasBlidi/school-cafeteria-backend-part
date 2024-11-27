package com.frew.crew.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDTO {
  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
}
