package org.example.coinsights;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RequiredArgsConstructor
public class CoInsightsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoInsightsApplication.class, args);
    }
}