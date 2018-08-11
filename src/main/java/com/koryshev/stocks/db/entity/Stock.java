package com.koryshev.stocks.db.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * An entity containing stock details.
 *
 * @author Ivan Koryshev
 */
@Entity
public class Stock extends AbstractPersistable<Integer> {

    private String name;
    private BigDecimal currentPrice;
    private Instant lastUpdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
