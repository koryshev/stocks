package com.koryshev.stocks.db.repository;

import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Tests functionality of {@link StockRepository}.
 *
 * @author Ivan Koryshev
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestUtil.class)
public class StockRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TestUtil testUtil;

    @Test
    public void getsAllStocks() {
        // Arrange
        List<Stock> testStocks = new ArrayList<>();
        testStocks.add(testUtil.createStock());
        testStocks.add(testUtil.createStock());
        stockRepository.saveAll(testStocks);

        // Act
        List<Stock> stocks = stockRepository.findAll();

        // Assert
        assertThat(stocks.containsAll(testStocks)).isTrue();
    }

    @Test
    public void createsOneStock() {
        // Arrange
        Stock testStock = testUtil.createStock();

        // Act
        stockRepository.save(testStock);
        Optional<Stock> stock = stockRepository.findById(testStock.getId());

        // Assert
        assertThat(stock.isPresent()).isTrue();
    }

    @Test
    public void updatesOneStock() {
        // Arrange
        Stock testStock = testUtil.createStock();
        testStock = stockRepository.save(testStock);

        // Act
        BigDecimal updatedPrice = new BigDecimal(TestUtil.RANDOM_INT.get());
        testStock.setCurrentPrice(updatedPrice);
        stockRepository.save(testStock);
        Stock stock = stockRepository.getOne(testStock.getId());

        // Assert
        assertThat(stock.getCurrentPrice()).isEqualTo(updatedPrice);
    }
}
