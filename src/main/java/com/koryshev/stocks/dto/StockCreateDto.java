package com.koryshev.stocks.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * A DTO for creating a stock.
 *
 * @author Ivan Koryshev
 */
public class StockCreateDto {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @NotNull
    @Min(value = 0)
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
