package org.example.coinsights.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Spring WebSocket 메세징 기능을 사용하기 위한 설정 클래스
 * STOMP(Simple Text Oriented Messaging Protocol) 기반의 브로드캐스팅을 구성하여 클라이언트와 실시간 연결을 지원한다.
 *
 * @author 최혁
 * @since 2020.07.07
 */

@Configuration
// STOMP(Simple Text Oriented Messaging Protocol) 설정을 지원하는 어노테이션
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * /topic 경로를 구독하는 클라이언트를 찾아 실시간 브로킹을 시작한다.
     *
     * @param config 메시지 브로커를 설정할 수 있는 MessageBrokerRegistry 객체
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    /**
     * 클라이언트가 WebSocket 서버에 연결할 수 있는 STOMP 엔드포인트를 등록한다.
     * "/ws" 경로로 연결할 수 있으며, CORS 정책을 우회하기 위해 모든 도메인 허용("*") 설정을 적용한다.
     *
     * @param registry STOMP 엔드포인트를 등록하기 위한 StompEndpointRegistry 객체
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }
}
