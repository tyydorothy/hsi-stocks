package com.bootcamp.bc_yahoo_finance.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bootcamp.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc_yahoo_finance.entity.comparators.StockSymbolComparator;
import com.bootcamp.bc_yahoo_finance.mapper.EntityMapper;
import com.bootcamp.bc_yahoo_finance.model.YHQuote;
import com.bootcamp.bc_yahoo_finance.repository.StockRepository;
import com.bootcamp.bc_yahoo_finance.util.RedisOperation;
import com.bootcamp.bc_yahoo_finance.util.SelectedStock;
import com.bootcamp.bc_yahoo_finance.util.yahoofinance.YHRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class AppRunner implements CommandLineRunner{

  @Autowired
  private YHRestClient restClient;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private RedisOperation redisOperation;

  @Autowired
  private EntityMapper entityMapper;




  @Override
  public void run(String... args) throws Exception{

    this.redisOperation.flushAll();
    
    if (stockRepository.count() == 0){
      this.saveStockEntityToDB();
    }

  }




  private void saveStockEntityToDB() throws JsonProcessingException{

    YHQuote quote = restClient.getQuote(SelectedStock.SYMBOL_LIST);

    List<StockEntity> stockEntities = SelectedStock.SYMBOL_MAP.entrySet().stream()
      .flatMap(k -> Arrays.asList(k.getValue()).stream().map(sym -> 
        entityMapper.map(
            sym, 
            k.getKey(), 
            quote.getQuoteResponse().getResult().stream().filter(e->e.getSymbol().equals(sym)).findFirst().get()
      )))
      .sorted(new StockSymbolComparator())
      .collect(Collectors.toList());

    stockRepository.saveAll(stockEntities);

    stockEntities.forEach(e->System.out.println(e));
  }


}
