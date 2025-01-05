package com.frew.crew.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
  private final SimpMessagingTemplate messagingTemplate;

  @Async
  public void notifyAdminAboutNewCommand(Command command) {
    try {
      System.out.println("Sending WebSocket message for command: " + command.getId());
      messagingTemplate.convertAndSend("/topic/Commands", command);
    } catch (Exception e) {
      System.err.println("Error sending WebSocket message: " + e.getMessage());
    }
  }
}
