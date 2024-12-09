package com.bootcamp.bc_yahoo_finance.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;
import com.bootcamp.bc_yahoo_finance.dto.StockDataDto;
import com.bootcamp.bc_yahoo_finance.dto.StockInfoDto;
import com.bootcamp.bc_yahoo_finance.entity.StockDataEntity;
import com.bootcamp.bc_yahoo_finance.entity.StockEntity;

@Component
public class DtoMapper {

  public StockDataDto map(StockDataEntity stockDataEntity){
    return StockDataDto.builder()
      .symbol(stockDataEntity.getStock().getSymbol())
      .regularMarketPrice(stockDataEntity.getRegularMarketPrice())
      .regularMarketTime(stockDataEntity.getRegularMarketTime())
      .regularMarketOpen(stockDataEntity.getRegularMarketOpen())
      .regularMarketPreviousClose(stockDataEntity.getRegularMarketPreviousClose())
      .regularMarketPriceAbsoluteChange(Calculator.calculatePriceAbsoluteChange(stockDataEntity.getRegularMarketPrice(), stockDataEntity.getRegularMarketPreviousClose()))
      .regularMarketPricePercentageChange(Calculator.calculatePricePercentageChange(stockDataEntity.getRegularMarketPrice(), stockDataEntity.getRegularMarketPreviousClose()))
      .build();
  }

  public StockInfoDto map(StockEntity stockEntity){
    return StockInfoDto.builder()
      .symbol(stockEntity.getSymbol())
      .shortName(stockEntity.getShortName())
      .longName(stockEntity.getLongName())
      .build();
  }

  private static class Calculator{
    private static Double calculatePriceAbsoluteChange(Double regularMarketPrice, Double regularMarketPreviousClose){
      return BigDecimal.valueOf(regularMarketPrice.doubleValue())
        .subtract(BigDecimal.valueOf(regularMarketPreviousClose.doubleValue()))
        .doubleValue();
    }
  
    private static Double calculatePricePercentageChange(Double regularMarketPrice, Double regularMarketPreviousClose){
      try{

        return BigDecimal.valueOf(regularMarketPrice.doubleValue())
          .subtract(BigDecimal.valueOf(regularMarketPreviousClose.doubleValue()))
          .divide(BigDecimal.valueOf(regularMarketPreviousClose.doubleValue()),4,RoundingMode.HALF_UP) 
          // if not rounded --> throw ArithmeticException due to non-terminating result (e.g. 0.3333333....)
          .multiply(BigDecimal.valueOf(100L))
          .doubleValue();
        
      }catch(ArithmeticException e){

        return null;
      
      }
    }
  }


}
