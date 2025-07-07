package org.example.coinsights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * 실시간 Kline 데이터를 STOMP를 통해 구독자에게 브로드캐스트하는 서비스 클래스
 *
 * SimpMessagingTemplate을 이용하여 /topic/kline 구독자에게 메시지를 전달한다.
 *
 * @author 최혁
 * @since 2025.07.07
 */
@Service
@RequiredArgsConstructor
public class WebSocketBroadcastService {

    // STOMP 메시지를 전송하기 위한 템플릿
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Kline 데이터를 STOMP 브로커 경로(/topic/kline)로 전송한다
     *
     * @param json 실시간 Kline JSON 데이터
     */
    public void sendKlineData(String json) {
        messagingTemplate.convertAndSend("/topic/kline", json);
    }
}
