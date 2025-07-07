package org.example.coinsights.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.example.coinsights.client.WebSocketClientFactory;
import org.example.coinsights.client.WebSocketClientInterface;

/**
 * WebSocket을 생성하여 서비스하는 클래스
 *
 * @author 최혁
 * @since 2025.07.07
 */

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final WebSocketClientFactory clientFactory;

    /**
     * WebSocketClient를 생성하고 연결을 시작한다.
     *
     * @param exchange 거래소 이름 (예: binance)
     * @param symbol  거래쌍 (예: BTCUSDT)
     * @param interval  차트 시간 간격 (예: 1m)
     */
    public void start(String exchange, String symbol, String interval) {
        WebSocketClientInterface client = clientFactory.getClient(exchange);
        client.connectToKline(symbol, interval);
    }

    /**
     * 연결 중인 거래소의 연결을 해제한다.
     *
     * @param exchange 거래소 이름 (예: binance)
     */
    public void stop(String exchange) {
        WebSocketClientInterface client = clientFactory.getClient(exchange);
        client.disconnect();
    }
}
