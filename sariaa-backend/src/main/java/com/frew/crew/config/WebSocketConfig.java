package com.frew.crew.config;

import com.frew.crew.command.CommandWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Autowired
  private CommandWebSocketHandler orderWebSocketHandler;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }


  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
      .setAllowedOriginPatterns("*")
      .withSockJS();
  }




  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(orderWebSocketHandler, "/orders")
      .setAllowedOrigins("*")  // Be more specific in production
      .withSockJS();  // Optional: for broader browser support
  }



  // Add this method for additional CORS configuration
  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration
      .setMessageSizeLimit(64 * 1024)  // Increase message size limit
      .setSendBufferSizeLimit(512 * 1024)
      .setSendTimeLimit(20000); // 20 seconds
  }
}
