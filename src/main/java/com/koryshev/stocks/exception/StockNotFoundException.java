package com.koryshev.stocks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a stock can't be found.
 *
 * @author Ivan Koryshev
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
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
