package com.frew.crew.command;

import com.frew.crew.articleCommand.ArticleCommand;
import com.frew.crew.articleCommand.ArticleCommandDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api/v1/commands")
@RequiredArgsConstructor
public class CommandController {
  private final CommandService commandService;

  private static final Logger logger = LoggerFactory.getLogger(CommandController.class);


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
    if (command.getStatus().equals(Status.NEW)){
      return ResponseEntity.ok(command) ;
    }
    else
      return ResponseEntity.ok(command);
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
