package com.frew.crew.command;

import com.frew.crew.articleCommand.ArticleCommandDTO;
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

  @GetMapping("/{userId}")
  public ResponseEntity<List<Command>> getCommandsByUserId(@PathVariable UUID userId){
    return ResponseEntity.ok(commandService.getCommandsByUserId(userId)) ;
  }

  @GetMapping("/active/{userId}")
  public ResponseEntity<List<Command>> getActiveCommandByUserId(
    @PathVariable UUID userId
  ){
      return ResponseEntity.ok(commandService.getActiveCommandsByUserId(userId)) ;
  }

  @PostMapping("/{userId}")
  public ResponseEntity<Command> createNewCommand(
          @PathVariable UUID userId,
          @RequestBody List<ArticleCommandDTO> articleCommandsDto
  ) {
    Command command = commandService.saveNewCommand(userId, articleCommandsDto) ;
      return ResponseEntity.ok(command) ;
  }


  @PatchMapping("/cooking/{commandId}")
  public ResponseEntity<Command> createCookingCommand(
          @PathVariable UUID commandId
  ) {

    return ResponseEntity.ok(commandService.saveCookingCommand(commandId));
  }

  @PatchMapping("/ready/{commandId}")
  public ResponseEntity<Command> createReadyCommand(
          @PathVariable UUID commandId
  ) {

    return ResponseEntity.ok(commandService.saveReadyCommand(commandId));
  }
  @PatchMapping("/completed/{commandId}")
  public ResponseEntity<Command> createCompletedCommand(
          @PathVariable UUID commandId
  ) {

    return ResponseEntity.ok(commandService.saveCompletedCommand(commandId));
  }

}
