package com.koryshev.stocks.controller;

import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.dto.StockCreateDto;
import com.koryshev.stocks.dto.StockDto;
import com.koryshev.stocks.dto.StockUpdateDto;
import com.koryshev.stocks.service.StockService;
import com.koryshev.stocks.util.StockMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * Provides an API for stocks.
 *
 * @author Ivan Koryshev
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    private final StockMapper stockMapper;

    public StockController(StockService stockService, StockMapper stockMapper) {
        this.stockService = Objects.requireNonNull(stockService, "stockService");
        this.stockMapper = Objects.requireNonNull(stockMapper, "stockMapper");
    }

    /**
     * Returns a list of all stocks.
     *
     * @return the stock list
     */
    @GetMapping
    public List<StockDto> get() {
        List<Stock> stocks = stockService.findAll();
        return stockMapper.toStockDto(stocks);
    }

    /**
     * Creates a new stock from data specified in a DTO.
     *
     * @param dto the DTO containing stock details
     * @return the created stock
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockDto create(@RequestBody @Valid StockCreateDto dto) {
        Stock stock = stockService.create(dto);
        return stockMapper.toStockDto(stock);
    }

    /**
     * Updates a stock with data specified in a DTO.
     *
     * @param stockId the stock ID to update
     * @param dto     the DTO containing data to update
     */
    @PutMapping("/{stockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer stockId, @RequestBody @Valid StockUpdateDto dto) {
        stockService.update(stockId, dto);
    }
}
