package com.koryshev.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Starts the Stocks application.
 *
 * @author Ivan Koryshev
 */
@SpringBootApplication
@EnableJpaAuditing
public class StocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksApplication.class, args);
    }
}
