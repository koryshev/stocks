package com.koryshev.stocks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a request attempts to make an invalid modifications of a stock.
 *
 * @author Ivan Koryshev
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockValidationException extends RuntimeException {

    public StockValidationException() {
    }

    public StockValidationException(String message) {
        super(message);
    }

    public StockValidationException(Throwable cause) {
        super(cause);
    }

    public StockValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
