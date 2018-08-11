package com.koryshev.stocks.dto;

import java.math.BigDecimal;

/**
 * A DTO for creating a stock.
 *
 * @author Ivan Koryshev
 */
public class StockCreateDto {

    private String name;
    private BigDecimal currentPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
