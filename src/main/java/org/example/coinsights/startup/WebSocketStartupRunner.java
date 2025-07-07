package org.example.coinsights.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.example.coinsights.service.WebSocketService;
import org.springframework.boot.context.event.ApplicationReadyEvent;

/**
 * 애플리케이션이 완전히 초기화된 후 WebSocket 연결을 자동으로 시작하는 클래스
 *
 * @author 최혁
 * @since 2025.07.07
 */
@Component
@RequiredArgsConstructor
public class WebSocketStartupRunner {

    private final WebSocketService webSocketService;

    /**
     * 애플리케이션 시작 직후 실행되어 Binance WebSocket 연결을 자동 시작함
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        webSocketService.start("binance", "BTCUSDT", "1m");
    }
}
