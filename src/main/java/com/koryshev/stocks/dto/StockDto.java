package com.koryshev.stocks.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * A complete DTO representing a stock.
 *
 * @author Ivan Koryshev
 */
@Getter
@Setter
public class StockDto {

    private Integer id;
    private String name;
    private BigDecimal currentPrice;
    private Instant createdDate;
    private Instant lastUpdate;
}
