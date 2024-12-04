package com.frew.crew.command;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class CommandWebSocketHandler extends TextWebSocketHandler {

  private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessions.add(session);
    super.afterConnectionEstablished(session);
    session.sendMessage(new TextMessage("Connection Established"));
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    for (WebSocketSession webSocketSession : sessions) {
      if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
        webSocketSession.sendMessage(message);
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
    sessions.remove(session);
    super.afterConnectionClosed(session, status);
  }

  // Method to send messages to all connected clients
  public void broadcastMessage(String message) {
    for (WebSocketSession session : sessions) {
      try {
        if (session.isOpen()) {
          session.sendMessage(new TextMessage(message));
        }
      } catch (IOException e) {
        // Handle exception
      }
    }
  }
}