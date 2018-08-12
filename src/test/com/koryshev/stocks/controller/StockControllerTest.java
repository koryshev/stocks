package com.koryshev.stocks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.dto.StockCreateDto;
import com.koryshev.stocks.dto.StockUpdateDto;
import com.koryshev.stocks.exception.StockNotFoundException;
import com.koryshev.stocks.service.StockService;
import com.koryshev.stocks.util.StockMapper;
import com.koryshev.stocks.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests functionality of {@link StockController}.
 *
 * @author Ivan Koryshev
 */
@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestUtil testUtil;

    @MockBean
    private StockService stockService;

    @Configuration
    @ComponentScan(basePackageClasses = {StockController.class, StockMapper.class, TestUtil.class})
    public static class StockControllerTestConfiguration {
    }

    @Test
    public void getsAllStocks() throws Exception {
        // Arrange
        List<Stock> testStocks = new ArrayList<>();
        testStocks.add(testUtil.createStock());
        testStocks.add(testUtil.createStock());

        when(stockService.findAll()).thenReturn(testStocks);

        // Act / Assert
        mvc.perform(get("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createsStock() throws Exception {
        // Arrange
        Stock testStock = testUtil.createStock();

        StockCreateDto dto = new StockCreateDto();
        dto.setName(testStock.getName());
        dto.setCurrentPrice(testStock.getCurrentPrice());

        when(stockService.create(dto)).thenReturn(testStock);

        // Act / Assert
        mvc.perform(post("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatesStock() throws Exception {
        // Arrange
        Stock testStock = testUtil.createStock();

        BigDecimal updatedPrice = new BigDecimal(TestUtil.RANDOM_INT.get());
        StockUpdateDto dto = new StockUpdateDto();
        dto.setCurrentPrice(updatedPrice);
        testStock.setCurrentPrice(updatedPrice);

        when(stockService.update(1, dto)).thenReturn(testStock);

        // Act / Assert
        mvc.perform(put("/api/stocks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void failsToUpdateNotExistingStock() throws Exception {
        // Arrange
        StockUpdateDto dto = new StockUpdateDto();
        dto.setCurrentPrice(new BigDecimal(TestUtil.RANDOM_INT.get()));

        when(stockService.update(any(), any())).thenThrow(StockNotFoundException.class);

        // Act / Assert
        mvc.perform(put("/api/stocks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
