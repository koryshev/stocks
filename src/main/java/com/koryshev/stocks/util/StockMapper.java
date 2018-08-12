package com.koryshev.stocks.util;

import com.koryshev.stocks.db.entity.Stock;
import com.koryshev.stocks.dto.StockCreateDto;
import com.koryshev.stocks.dto.StockDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Maps {@link Stock} entities to and from various DTOs.
 *
 * @author Ivan Koryshev
 */
@Mapper(componentModel = "spring")
public interface StockMapper {

    StockDto toStockDto(Stock stock);

    List<StockDto> toStockDto(List<Stock> stocks);

    Stock fromStockCreateDto(StockCreateDto dto);

}
