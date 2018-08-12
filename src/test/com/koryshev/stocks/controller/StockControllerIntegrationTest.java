package com.koryshev.stocks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.db.repository.StockRepository;
import com.koryshev.stocks.dto.StockCreateDto;
import com.koryshev.stocks.dto.StockUpdateDto;
import com.koryshev.stocks.service.StockService;
import com.koryshev.stocks.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests functionality of {@link StockController} using real implementation of service layer.
 *
 * @author Ivan Koryshev
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerIntegrationTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Before
    public void clearDb() {
        stockRepository.deleteAll();
    }

    @Test
    public void getsAllStocks() throws Exception {
        // Arrange
        List<Stock> testStocks = new ArrayList<>();
        testStocks.add(testUtil.createStock());
        testStocks.add(testUtil.createStock());
        stockRepository.saveAll(testStocks);

        // Act / Assert
        mvc.perform(get("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getsOneStock() throws Exception {
        // Arrange
        Stock testStock = testUtil.createStock();
        testStock = stockRepository.save(testStock);

        // Act / Assert
        mvc.perform(get("/api/stocks/" + testStock.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testStock.getName())));
    }

    @Test
    public void failsToGetNotExistingStock() throws Exception {
        // Arrange -- no-op

        // Act / Assert
        mvc.perform(get("/api/stocks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createsStock() throws Exception {
        // Arrange
        StockCreateDto dto = new StockCreateDto();
        dto.setName("test-stock");
        dto.setCurrentPrice(new BigDecimal(TestUtil.RANDOM_INT.get()));

        // Act / Assert
        mvc.perform(post("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(dto.getName())));
    }

    @Test
    public void failsToCreateStockWithNotUniqueName() throws Exception {
        // Arrange
        Stock stock = testUtil.createStock();
        stockRepository.save(stock);

        StockCreateDto dto = new StockCreateDto();
        dto.setName(stock.getName());
        dto.setCurrentPrice(new BigDecimal(TestUtil.RANDOM_INT.get()));

        // Act / Assert
        mvc.perform(post("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatesStock() throws Exception {
        // Arrange
        Stock stock = testUtil.createStock();
        stock = stockRepository.save(stock);

        StockUpdateDto dto = new StockUpdateDto();
        dto.setCurrentPrice(new BigDecimal(TestUtil.RANDOM_INT.get()));

        // Act / Assert
        mvc.perform(put("/api/stocks/" + stock.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void failsToUpdateNotExistingStock() throws Exception {
        // Arrange
        StockUpdateDto dto = new StockUpdateDto();
        dto.setCurrentPrice(new BigDecimal(TestUtil.RANDOM_INT.get()));

        // Act / Assert
        mvc.perform(put("/api/stocks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
