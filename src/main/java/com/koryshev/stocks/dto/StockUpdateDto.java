package com.koryshev.stocks.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A DTO for updating a stock.
 *
 * @author Ivan Koryshev
 */
public class StockUpdateDto {

    @NotNull
    @Min(value = 0)
    private BigDecimal currentPrice;

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
}
