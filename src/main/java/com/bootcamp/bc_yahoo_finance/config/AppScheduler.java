package com.bootcamp.bc_yahoo_finance.config;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bootcamp.bc_yahoo_finance.entity.StockDataEntity;
import com.bootcamp.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc_yahoo_finance.mapper.DtoMapper;
import com.bootcamp.bc_yahoo_finance.mapper.EntityMapper;
import com.bootcamp.bc_yahoo_finance.model.YHQuote;
import com.bootcamp.bc_yahoo_finance.repository.StockDataRepository;
import com.bootcamp.bc_yahoo_finance.repository.StockRepository;
import com.bootcamp.bc_yahoo_finance.util.RedisOperation;
import com.bootcamp.bc_yahoo_finance.util.yahoofinance.YHRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AppScheduler {

  @Autowired
  private YHRestClient restClient;

  @Autowired
  private RedisOperation redisOperation;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private StockDataRepository stockDataRepository;

  @Autowired
  private EntityMapper entityMapper;

  @Autowired
  private DtoMapper dtoMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Scheduled (cron = "0 55 8 * * *")
  public void clearCache(){
    this.redisOperation.flushAll();
  }

  @Scheduled (cron = "0 0/5 9-15 * * 1-5")
  public void saveData() 
      throws JsonProcessingException{

      List<StockEntity> stockEntities = stockRepository.findAll();

      YHQuote quote = restClient.getQuote(stockEntities.stream().map(e->e.getSymbol()).toList());
      
      List<StockDataEntity> stockDataEntities = quote.getQuoteResponse().getResult().stream()
          .map(q -> {
            StockDataEntity stockDataEntity = entityMapper.map(q);
            stockDataEntity.setStock(stockEntities.stream().filter(e->e.getSymbol().equals(q.getSymbol())).findFirst().get());
            return stockDataEntity;
          })
          .toList();
  
      stockDataRepository.saveAll(stockDataEntities);
  
      String stockDataJson = objectMapper.writeValueAsString(stockDataEntities.stream().map(e->dtoMapper.map(e)).toArray());
  
      redisOperation.set("LATEST", stockDataJson);
    
  }


}
