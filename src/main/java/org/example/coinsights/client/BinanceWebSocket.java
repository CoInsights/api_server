package org.example.coinsights.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import java.net.URI;

/**
 * 실시간 가격 정보를 WebSocket으로 구독하는 Binance 클라이언트 구현체
 * 클라이언트로 받은 데이터를 /topic/kline 으로 브로드캐스트한다.
 *
 * @author 최혁
 * @since 2025.07.07
 */
@Slf4j
@Component("binanceWebSocketClient")
@RequiredArgsConstructor
class BinanceWebSocketClient implements WebSocketClientInterface {

    private final SimpMessagingTemplate messagingTemplate;
    private WebSocketClient webSocketClient;

    @Override
    public void connectToKline(String symbol, String interval) {
        try {
            String endpoint = String.format("wss://stream.binance.com:9443/ws/%s@kline_%s",
                    symbol.toLowerCase(), interval);

            webSocketClient = new WebSocketClient(new URI(endpoint)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    log.info("✅ Binance WebSocket 연결 성공");
                }

                @Override
                public void onMessage(String message) {
                    log.info("📥 Binance 차트 데이터 수신: {}", message);

                    // 메세지를 전환하여 /topic/kline을 구독 중인 클라이언트로 메세지를 실시간 전달을 한다.
                    messagingTemplate.convertAndSend("/topic/kline", message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.warn("⚠️ Binance WebSocket 종료: {}, 원격 종료 여부: {}", reason, remote);
                }

                @Override
                public void onError(Exception ex) {
                    log.error("❌ Binance WebSocket 오류", ex);
                }
            };

            webSocketClient.connect();
        } catch (Exception e) {
            log.error("Binance WebSocket 연결 실패", e);
        }
    }

    @Override
    public void disconnect() {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }
}
