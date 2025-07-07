package org.example.coinsights.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * WebSocketClient를 생성하는 펙토리 클래스
 *
 * @author 최혁
 * @since 2025.07.07
 */
@Component
public class WebSocketClientFactory {

    private final WebSocketClientInterface binanceClient;
    private final WebSocketClientInterface mexcClient;

    /**
     * WebSocketClientFactory 생성자 메소드
     *
     * @return WebSocketClient 구현제
     */
    public WebSocketClientFactory(
            @Qualifier("binanceWebSocketClient") WebSocketClientInterface binanceClient,
            @Qualifier("mexcWebSocketClient") WebSocketClientInterface mexcClient
    ) {
        this.binanceClient = binanceClient;
        this.mexcClient = mexcClient;
    }
    /**
     * 거래소의 이름을 param으로 받아 WebSocketClient를 생성한다.
     * 생성 가능한 거래소: binance, mexc
     *
     * @param exchangeName  거래소 이름 (예: binance)
     * @return WebSocketClient 구현제
     */
    public WebSocketClientInterface getClient(String exchangeName) {
        return switch (exchangeName.toLowerCase()) {
            case "binance" -> binanceClient;
            case "mexc" -> mexcClient;
            default -> throw new IllegalArgumentException("지원하지 않는 거래소: " + exchangeName);
        };
    }
}
