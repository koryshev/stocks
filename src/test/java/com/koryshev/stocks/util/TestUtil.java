package com.koryshev.stocks.util;

import com.koryshev.stocks.db.entity.Stock;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Tests-related utility methods.
 *
 * @author Ivan Koryshev
 */
@Component
public class TestUtil {

    private static final Random RANDOM = new Random();

    public static final Supplier<Integer> RANDOM_INT = () -> RANDOM.nextInt(Integer.MAX_VALUE);

    public Stock createStock() {
        Stock stock = new Stock();
        stock.setName("stock-" + RANDOM_INT.get());
        stock.setCurrentPrice(new BigDecimal(RANDOM_INT.get()));
        return stock;
    }
}
