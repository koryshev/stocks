package com.koryshev.stocks.exception;

/**
 * Thrown when a stock can't be found.
 *
 * @author Ivan Koryshev
 */
public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException() {
    }

    public StockNotFoundException(String message) {
        super(message);
    }

    public StockNotFoundException(Throwable cause) {
        super(cause);
    }

    public StockNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
