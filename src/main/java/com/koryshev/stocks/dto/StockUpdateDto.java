package com.koryshev.stocks.dto;

import java.math.BigDecimal;

/**
 * A DTO for updating a stock.
 *
 * @author Ivan Koryshev
 */
public class StockUpdateDto {

    private Integer id;
    private BigDecimal currentPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
