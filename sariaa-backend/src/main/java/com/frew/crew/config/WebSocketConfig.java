package com.frew.crew.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


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
  // Add this method for additional CORS configuration
  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration
      .setMessageSizeLimit(64 * 1024)  // Increase message size limit
      .setSendBufferSizeLimit(512 * 1024)
      .setSendTimeLimit(20000); // 20 seconds
  }
}
