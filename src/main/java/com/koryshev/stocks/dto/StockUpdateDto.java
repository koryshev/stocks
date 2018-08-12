package com.koryshev.stocks.dto;

import java.math.BigDecimal;

/**
 * A DTO for updating a stock.
 *
 * @author Ivan Koryshev
 */
public class StockUpdateDto {

    private BigDecimal currentPrice;

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
