package com.frew.crew.command;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/commands")
@RequiredArgsConstructor
public class CommandController {
  private final CommandService commandService;

  @GetMapping
  public ResponseEntity<List<Command>> getAllCommands() {
    List<Command> commands = commandService.getAllCommands();
    return ResponseEntity.ok(commands);
  }

//  @PostMapping("/{userId}")
//  public ResponseEntity<Command> saveCommand(
//    @PathVariable UUID userId,
//    @RequestBody(required = false) List<ArticleCommand> articles
//  ) {
//    Command command = commandService.saveCommand(userId, articles);
//    return new ResponseEntity<>(command, HttpStatus.CREATED);
//  }

  @PostMapping("/{userId}")
  public ResponseEntity<Command> createCommand(@PathVariable UUID userId) {
    return ResponseEntity.ok(commandService.saveCommand(userId));
  }
}
