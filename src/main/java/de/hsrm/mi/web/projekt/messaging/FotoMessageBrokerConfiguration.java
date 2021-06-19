package de.hsrm.mi.web.projekt.messaging;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class FotoMessageBrokerConfiguration implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Prefix für alle zugehörigen Destinations
        // z.B. /topic/news, /topic/offers usw.
        registry.enableSimpleBroker("/topic");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // setAllowedOrigins("*") hebt Origin-Überprüfung bei Verbindungsaufbau auf
        // (Vereinfachung, aber weniger sicher – vgl. CORS;
        // im Produktionsbetrieb vermeiden)
        registry.addEndpoint("/messagebroker").setAllowedOrigins("*");
    }

}
