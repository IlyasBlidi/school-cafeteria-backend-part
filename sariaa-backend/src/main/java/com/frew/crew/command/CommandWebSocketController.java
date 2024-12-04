package com.frew.crew.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@Controller
@RequiredArgsConstructor
public class CommandWebSocketController {

  private final SimpMessagingTemplate messagingTemplate; // Add `final` to inject properly
  private final CommandRepository commandRepository;


  @Async
  public void notifyAdminAboutNewCommand(Command command) {
    try {


      // Log before sending
      System.out.println("Sending WebSocket message for command: " + command.getId());

      messagingTemplate.convertAndSend("/topic/newCommands", command);
    } catch (Exception e) {
      // Log any errors in sending
      System.err.println("Error sending WebSocket message: " + e.getMessage());
    }
  }

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  @Transactional
  public void confirmCommand(UUID commandId) {
    Command command = commandRepository.findById(commandId)
      .orElseThrow(() -> new RuntimeException("Command not found"));
    command.setStatus(Status.ACCEPTED);
    commandRepository.save(command);

    notifyAdminAboutNewCommand(command); // Notify via WebSocket
  }
}
