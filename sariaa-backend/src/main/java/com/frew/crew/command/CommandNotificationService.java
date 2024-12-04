package com.frew.crew.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandNotificationService {

  @Autowired
  private CommandWebSocketHandler webSocketHandler;

  public void notifyAboutNewOrder(Command command) {
    // Convert command to JSON
    String orderJson = convertCommandToJson(command);

    // Broadcast to all connected clients
    webSocketHandler.broadcastMessage(orderJson);
  }

  private String convertCommandToJson(Command command) {
    // Use ObjectMapper or your preferred JSON conversion method
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(command);
    } catch (JsonProcessingException e) {
      // Handle exception
      return "{}";
    }
  }
}