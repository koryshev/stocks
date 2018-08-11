package com.koryshev.stocks.util;

import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.dto.StockCreateDto;
import org.mapstruct.Mapper;

/**
 * Maps {@link Stock} entities to and from various DTOs.
 *
 * @author Ivan Koryshev
 */
@Mapper(componentModel = "spring")
public interface StockMapper {

    Stock fromStockCreateDto(StockCreateDto dto);

}
