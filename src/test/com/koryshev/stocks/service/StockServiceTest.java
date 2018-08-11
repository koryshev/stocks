package com.koryshev.stocks.service;

import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.db.repository.StockRepository;
import com.koryshev.stocks.dto.StockCreateDto;
import com.koryshev.stocks.dto.StockUpdateDto;
import com.koryshev.stocks.exception.StockNotFoundException;
import com.koryshev.stocks.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests functionality of {@link StockService}.
 *
 * @author Ivan Koryshev
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private TestUtil testUtil;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void getsAllStocks() {
        // Arrange
        List<Stock> testStocks = new ArrayList<>();
        testStocks.add(testUtil.createStock());
        testStocks.add(testUtil.createStock());

        when(stockRepository.findAll()).thenReturn(testStocks);

        // Act
        List<Stock> stocks = stockService.findAll();

        // Assert
        assertThat(stocks.containsAll(testStocks)).isTrue();
    }

    @Test
    public void createsOneStock() {
        // Arrange
        Stock testStock = testUtil.createStock();
        StockCreateDto dto = new StockCreateDto();
        dto.setName(testStock.getName());
        dto.setCurrentPrice(testStock.getCurrentPrice());

        when(stockRepository.save(any())).thenReturn(testStock);

        // Act
        Stock stock = stockService.create(dto);

        // Assert
        assertThat(stock).isEqualTo(testStock);
    }

    @Test
    public void updatesOneStock() {
        // Arrange
        Stock testStock = testUtil.createStock();
        when(stockRepository.findById(any())).thenReturn(Optional.of(testStock));

        BigDecimal updatedPrice = new BigDecimal(TestUtil.RANDOM_INT.get());
        StockUpdateDto dto = new StockUpdateDto();
        dto.setCurrentPrice(updatedPrice);

        Stock updatedStock = new Stock();
        updatedStock.setName(testStock.getName());
        updatedStock.setCurrentPrice(updatedPrice);

        when(stockRepository.save(testStock)).thenReturn(updatedStock);

        // Act
        Stock stock = stockService.update(dto);

        // Assert
        assertThat(stock.getCurrentPrice()).isEqualTo(updatedPrice);
    }

    @Test(expected = StockNotFoundException.class)
    public void failsToUpdateNotExistingStock() {
        // Arrange
        when(stockRepository.findById(any())).thenReturn(Optional.empty());

        StockUpdateDto dto = new StockUpdateDto();
        dto.setId(1);
        dto.setCurrentPrice(new BigDecimal(TestUtil.RANDOM_INT.get()));

        // Act
        stockService.update(dto);
    }
}
