package com.koryshev.stocks.service;

import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.db.repository.StockRepository;
import com.koryshev.stocks.dto.StockCreateDto;
import com.koryshev.stocks.dto.StockUpdateDto;
import com.koryshev.stocks.exception.StockNotFoundException;
import com.koryshev.stocks.util.StockMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Manages {@link Stock} entries.
 *
 * @author Ivan Koryshev
 */
@Service
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;

    public StockService(StockMapper stockMapper, StockRepository stockRepository) {
        this.stockMapper = Objects.requireNonNull(stockMapper, "stockMapper");
        this.stockRepository = Objects.requireNonNull(stockRepository, "stockRepository");
    }

    /**
     * Returns a list of all stocks.
     *
     * @return the stock list
     */
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    /**
     * Creates a new stock from data specified in a DTO.
     *
     * @param dto the DTO containing stock details
     * @return the created stock
     */
    public Stock create(StockCreateDto dto) {
        Stock stock = stockMapper.fromStockCreateDto(dto);
        return stockRepository.save(stock);
    }

    /**
     * Updates a stock with data specified in a DTO.
     *
     * @param dto the DTO containing data to update
     * @return the updated stock
     * @throws StockNotFoundException if such stock doesn't exist
     */
    public Stock update(StockUpdateDto dto) {
        Stock stock = stockRepository.findById(dto.getId())
                .orElseThrow(() -> new StockNotFoundException("Stock not found"));
        stock.setCurrentPrice(dto.getCurrentPrice());
        return stockRepository.save(stock);
    }
}
