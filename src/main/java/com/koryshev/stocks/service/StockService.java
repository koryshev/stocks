package com.koryshev.stocks.service;

import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.db.repository.StockRepository;
import com.koryshev.stocks.dto.StockCreateDto;
import com.koryshev.stocks.dto.StockUpdateDto;
import com.koryshev.stocks.exception.StockNotFoundException;
import com.koryshev.stocks.exception.StockValidationException;
import com.koryshev.stocks.util.StockMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manages {@link Stock} entries.
 *
 * @author Ivan Koryshev
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    @NonNull
    private final StockMapper stockMapper;

    @NonNull
    private final StockRepository stockRepository;

    /**
     * Returns a list of all stocks.
     *
     * @return the stock list
     */
    public List<Stock> findAll() {
        log.info("Getting all stock entries");
        List<Stock> stocks = stockRepository.findAll();
        log.info("Returning {} entries", stocks.size());
        return stocks;
    }

    /**
     * Returns a stock with the specified ID.
     *
     * @param stockId the stock ID to return
     * @return the stock
     * @throws StockNotFoundException if such stock doesn't exist
     */
    public Stock findOne(Integer stockId) {
        log.info("Getting a stock, ID = {}", stockId);
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> {
                    log.warn("Attempted to get a stock with the ID = {}, which was not found", stockId);
                    return new StockNotFoundException("Stock not found");
                });
        log.info("Returning a stock, ID = {}, name = {}", stockId, stock.getName());
        return stock;
    }

    /**
     * Creates a new stock from data specified in a DTO.
     *
     * @param dto the DTO containing stock details
     * @return the created stock
     * @throws StockValidationException if the specified stock name is already in use
     */
    public Stock create(StockCreateDto dto) {
        log.info("Creating a stock, name = {}, price = {}", dto.getName(), dto.getCurrentPrice());
        Stock stock = stockMapper.fromStockCreateDto(dto);
        try {
            stock = stockRepository.save(stock);
            log.info("Created a stock, ID = {}", stock.getId());
            return stock;
        } catch (DataIntegrityViolationException e) {
            log.warn("Attempted to create a stock with the name = {}, which is already in use", dto.getName());
            throw new StockValidationException("Stock name must be unique");
        }
    }

    /**
     * Updates a stock with data specified in a DTO.
     *
     * @param stockId the stock ID to update
     * @param dto     the DTO containing data to update
     * @return the updated stock
     * @throws StockNotFoundException if such stock doesn't exist
     */
    public Stock update(Integer stockId, StockUpdateDto dto) {
        log.info("Updating a stock, ID = {}, updated price = {}", stockId, dto.getCurrentPrice());
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> {
                    log.warn("Attempted to update a stock with the ID = {}, which was not found", stockId);
                    return new StockNotFoundException("Stock not found");
                });

        stock.setCurrentPrice(dto.getCurrentPrice());
        stock = stockRepository.save(stock);
        log.info("Updated a stock, ID = {}", stock.getId());
        return stock;
    }
}
