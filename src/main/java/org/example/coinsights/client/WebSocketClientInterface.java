package org.example.coinsights.client;

/**
 * 실시간 가격 정보를 WebSocket으로 구독하는 클라이언트 인터페이스
 *
 * @author 최혁
 * @since 2025.07.07
 */
public interface WebSocketClientInterface {
    /**
     * WebSocket 연결을 통해 실시간 차트 데이터를 구독한다.
     *
     * 차트 시간 간격 동안 변화가 감지될 때마다 데이터를 보낸다.
     * "x": false인 경우 → 아직 해당 Kline(예: 1분 봉)이 끝나지 않았고, 실시간으로 계속 업데이트되는 중간 상태임
     * "x": true인 경우 → 해당 (예: 1분) 봉이 종료되어 최종 확정된 Kline 데이터
     *
     * 메세지가 올때마다 브로드캐스팅을 통해 메세지를 구독중인 클라이언트로 전달한다.
     *
     * @param symbol  거래쌍 (예: BTCUSDT)
     * @param interval  차트 시간 간격 (예: 1m)
     */
    void connectToKline(String symbol, String interval);

    /**
     * WebSocket 연결을 해제한다.
     */
    void disconnect();
}