package org.example.coinsights.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정 클래스
 *
 * WebSocket 통신 및 STOMP 메시징을 위한 인증 예외 처리와
 * WebSocket 연결 시 필수로 필요한 CSRF 비활성화 설정을 포함한다.
 *
 * 현재는 모든 요청에 대해 인증 없이 접근이 가능하도록 설정되어 있으며,
 * 추후 인증 및 인가가 필요한 엔드포인트가 추가될 경우 설정을 수정할 수 있다.
 *
 * @author 최혁
 * @since 2025.07.08
 */
@Configuration
public class SecurityConfig {

    /**
     * Spring Security의 HTTP 보안 설정을 정의한다
     *
     * - "/ws/**" : STOMP용 WebSocket 엔드포인트는 인증 없이 접근 허용
     * - "/topic/**" : 메시지 브로커 채널 구독 주소도 인증 없이 허용
     * - 나머지 모든 요청도 현재는 permitAll()로 열려 있음
     *
     * - CSRF 비활성화 : WebSocket 통신은 브라우저의 HTTP 세션을 사용하지 않으므로 CSRF 보호가 필요 없음
     *
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain : 최종 필터 체인 객체
     * @throws Exception 예외 발생 시 처리
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()     // STOMP WebSocket 연결 허용
                        .requestMatchers("/topic/**").permitAll()  // 메시지 브로커 경로 허용
                        .anyRequest().permitAll()                 // 기타 모든 요청 허용 (추후 필요 시 authenticated()로 변경)
                )
                .csrf(csrf -> csrf.disable());                // CSRF 비활성화 (WebSocket은 세션 쿠키를 사용하지 않음)

        return http.build();
    }
}
