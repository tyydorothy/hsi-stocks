package com.bootcamp.bc_yahoo_finance.mapper;

import org.springframework.stereotype.Component;
import com.bootcamp.bc_yahoo_finance.entity.StockDataEntity;
import com.bootcamp.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc_yahoo_finance.model.YHQuote;
import com.bootcamp.bc_yahoo_finance.util.enums.StockGroup;

@Component
public class EntityMapper{

  public StockEntity map(String symbol,StockGroup stockGroup){
    return StockEntity.builder()
      .symbol(symbol)
      .stockGroup(stockGroup)
      .build();
  }

  public StockEntity map(String symbol,StockGroup stockGroup,YHQuote.QuoteResponse.Result result){
    return StockEntity.builder()
      .symbol(symbol)
      .stockGroup(stockGroup)
      .shortName(result.getShortName())
      .longName(result.getLongName())
      .build();
  }

  public StockDataEntity map(YHQuote.QuoteResponse.Result result){
    return StockDataEntity.builder()
      .regularMarketTime(result.getRegularMarketTime())
      .regularMarketPrice(result.getRegularMarketPrice())
      .bid(result.getBid())
      .bidSize(result.getBidSize())
      .ask(result.getAsk())
      .askSize(result.getAskSize())
      .regularMarketOpen(Double.parseDouble(result.getRegularMarketOpen()))
      .regularMarketPreviousClose(Double.parseDouble(result.getRegularMarketPreviousClose()))
      .build();
  }
  
}

