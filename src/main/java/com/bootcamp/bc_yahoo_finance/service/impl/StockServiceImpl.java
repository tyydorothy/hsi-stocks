package com.bootcamp.bc_yahoo_finance.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bootcamp.bc_yahoo_finance.dto.StockDataDto;
import com.bootcamp.bc_yahoo_finance.dto.StockInfoDto;
import com.bootcamp.bc_yahoo_finance.entity.StockDataEntity;
import com.bootcamp.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc_yahoo_finance.entity.comparators.StockDataChronologicalComparator;
import com.bootcamp.bc_yahoo_finance.mapper.DtoMapper;
import com.bootcamp.bc_yahoo_finance.repository.StockDataRepository;
import com.bootcamp.bc_yahoo_finance.repository.StockRepository;
import com.bootcamp.bc_yahoo_finance.service.StockService;
import com.bootcamp.bc_yahoo_finance.util.RedisOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class StockServiceImpl implements StockService{

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private StockDataRepository stockDataRepository;

  @Autowired
  private RedisOperation redisOperation;

  @Autowired
  private DtoMapper dtoMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private StockDataChronologicalComparator stockDataChronologicalComparator;



  @Override
  public List<StockInfoDto> getStockInfoList(){
    return stockRepository.findAll().stream()
      .map(e -> dtoMapper.map(e))
      .toList();
  }

  @Override
  public List<StockDataDto> getLatestStockData() throws JsonProcessingException{
    if (redisOperation.get("LATEST") != null)
      return Arrays.asList(objectMapper.readValue(redisOperation.get("LATEST"), StockDataDto[].class));
          
    List<StockDataDto> stockDataDtos = new ArrayList<>();

    List<StockDataEntity> stockDataEntities = stockDataRepository.findAll();

    for (StockEntity stock: stockRepository.findAll()){
      stockDataDtos.add(
        stockDataEntities.stream()
        .filter(e->e.getStock().equals(stock))
        .sorted(stockDataChronologicalComparator.reversed())
        .findFirst()
        .map(e->dtoMapper.map(e))
        .get()
      );
    }
  
    String stockDataJson = objectMapper.writeValueAsString(stockDataDtos.toArray());

    redisOperation.set("LATEST", stockDataJson);
    
    return Arrays.asList(objectMapper.readValue(redisOperation.get("LATEST"), StockDataDto[].class));

  }

  @Override
  public List<StockDataDto> getLatestStockData(String group) throws JsonProcessingException{

    List<StockDataDto> stockDataDtos = this.getLatestStockData();

    List<String> stockSymbols = stockRepository.findAll().stream()
      .filter(e->e.getStockGroup().name().equals(group.toUpperCase()))
      .map(e->e.getSymbol())
      .toList();

    List<StockDataDto> stockGroupDataDtos = new ArrayList<>();

    for(String symbol : stockSymbols){
      stockGroupDataDtos.add(
        stockDataDtos.stream()
        .filter(e->e.getSymbol().equals(symbol))
        .findFirst()
        .get()
      );
    }
        
    return stockGroupDataDtos;

  }


}
