package server;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
/**
 * This class manages the configuration of the WebSockets
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry){
    registry.addEndpoint("/websocket");
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config){
    config.setApplicationDestinationPrefixes("/app");
    config.enableSimpleBroker("/topic");
  }
}

