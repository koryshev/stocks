package com.koryshev.stocks.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * A DTO for creating a stock.
 *
 * @author Ivan Koryshev
 */
@Getter
@Setter
public class StockCreateDto {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Min(value = 0)
    private BigDecimal currentPrice;
}
