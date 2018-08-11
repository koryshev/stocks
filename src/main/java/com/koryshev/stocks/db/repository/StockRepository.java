package com.koryshev.stocks.db.repository;

import com.koryshev.stocks.db.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository for accessing {@link Stock}.
 *
 * @author Ivan Koryshev
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

}
