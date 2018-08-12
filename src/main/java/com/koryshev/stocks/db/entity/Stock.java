package com.koryshev.stocks.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * An entity containing stock details.
 *
 * @author Ivan Koryshev
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Getter
@Setter
public class Stock extends AbstractPersistable<Integer> {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal currentPrice;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastUpdate;
}
