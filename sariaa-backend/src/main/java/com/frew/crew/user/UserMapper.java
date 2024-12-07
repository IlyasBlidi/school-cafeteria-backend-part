package com.frew.crew.user;

public class UserMapper {
  private UserMapper() {
    // Prevent instantiation
  }

  public static UserDTO toUserDTO(User user) {
    return new UserDTO(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail(),
        user.getRole(),
        user.getCard() != null ? user.getCard() : null
    );
  }

  public static UserSummaryDTO toUserSummaryDTO(User user) {
    return new UserSummaryDTO(
        user.getId(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail()
    );
  }
}
