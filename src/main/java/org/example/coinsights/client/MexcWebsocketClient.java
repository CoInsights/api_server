package org.example.coinsights.client;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;
import java.net.URI;

/**
 * 실시간 가격 정보를 WebSocket으로 구독하는 mexc 클라이언트 구현체
 *
 * @author 최혁
 * @since 2025.07.07
 *
 * TODO: protobuf 전환 구현, 브로드캐스팅 적용
 *
 */
@Slf4j
@Component("mexcWebSocketClient")
class MexcWebSocketClient implements WebSocketClientInterface {
    private WebSocketClient webSocketClient;

    @Override
    public void connectToKline(String symbol, String interval) {
        try {
            String endpoint = "wss://wbs.mexc.com/ws";

            webSocketClient = new WebSocketClient(new URI(endpoint)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    log.info("✅ MEXC WebSocket 연결 성공");

                    String msg = String.format("""
                        { "method": "SUBSCRIPTION", "params": ["spot@public.deals.v3.api.pb@%s"] }
                    """, symbol.toUpperCase());

                    send(msg);
                    log.info("📨 MEXC 구독 메시지 전송: {}", msg);
                }

                @Override
                public void onMessage(String message) {
                    log.info("📥 MEXC 차트 데이터 수신: {}", message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.warn("⚠️ MEXC WebSocket 종료: {}, 원격 종료 여부: {}", reason, remote);
                }

                @Override
                public void onError(Exception ex) {
                    log.error("❌ MEXC WebSocket 오류", ex);
                }
            };

            webSocketClient.connect();
        } catch (Exception e) {
            log.error("MEXC WebSocket 연결 실패", e);
        }
    }

    @Override
    public void disconnect() {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }
}
