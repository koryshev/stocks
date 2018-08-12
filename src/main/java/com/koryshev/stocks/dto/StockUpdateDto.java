package com.koryshev.stocks.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A DTO for updating a stock.
 *
 * @author Ivan Koryshev
 */
@Getter
@Setter
public class StockUpdateDto {

    @NotNull
    @Min(value = 0)
    private BigDecimal currentPrice;
}
