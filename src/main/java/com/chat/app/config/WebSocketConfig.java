package com.chat.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers STOMP endpoints that clients will use to connect to the WebSocket server.
     * * @param registry The registry used to configure the endpoint paths and options.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registers the "/chat" endpoint, enabling the handshake for WebSocket connections
        registry.addEndpoint("/chat")
                // Restricts access to the specified frontend origin to prevent CSRF attacks
                .setAllowedOrigins("http://localhost:8080")
                // Enables SockJS fallback options for browsers that don't support native WebSockets
                .withSockJS();
    }

    /**
     * Configures the message broker options, defining how messages are routed
     * between the clients and the server.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 1. Enable a simple memory-based broker to carry messages back to the client
        // Destinations starting with "/topic" are usually for "Pub/Sub" (one-to-many)
        registry.enableSimpleBroker("/topic");

        // 2. Designate the prefix for messages bound for methods annotated with @MessageMapping
        // When a client sends a message to "/app/chat", it is routed to your Controller
        registry.setApplicationDestinationPrefixes("/app");
    }
}
