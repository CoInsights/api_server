package org.example.coinsights;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.example.coinsights.service.WebSocketService;

@SpringBootTest
@ActiveProfiles("test")
class WebSocketClientIntegrationTest {

    @Autowired
    private WebSocketService webSocketService;

    @Test
    void binanceWebSocketClient_shouldReceiveMessages() throws InterruptedException {
        webSocketService.start("binance", "BTCUSDT", "1m");
        Thread.sleep(15000);
        webSocketService.stop("binance");
    }
}
