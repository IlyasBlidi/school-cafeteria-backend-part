package com.frew.crew.command;

import static com.frew.crew.user.UserMapper.toUserSummaryDTO;

public class CommandMapper {
  public static CommandDTO toCommandDTO(Command command) {
    return new CommandDTO(
      command.getId(),
      command.getTotalPrice(),
      command.getCommandDate(),
      command.getUser() != null ? toUserSummaryDTO(command.getUser()) : null
    );
  }
}
