package com.czy.echat.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


/**
  * Spring websocket configuration class
  */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
     /**
      * Define the wensocket connection when receiving/websocket, adding HttpSessionHandshakeInterceptor is to change the attributes in httpsession before websocket handshake
      * Added to websocket session, withSockJS adds support for sockJS
      *
      * @param stompEndpointRegistry
      */
     public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
         stompEndpointRegistry.addEndpoint("/websocket").addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
     }

     /**
      * Configure the message agent, the url headed by /app will go through MessageMapping first
      * /topic directly enter the message broker
      *
      * @param registry
      */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
