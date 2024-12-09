package com.bootcamp.bc_yahoo_finance.controller.restcontroller.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.bc_yahoo_finance.controller.restcontroller.StockOperation;
import com.bootcamp.bc_yahoo_finance.dto.StockDataDto;
import com.bootcamp.bc_yahoo_finance.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class StockController implements StockOperation{

  @Autowired
  private StockService stockService;

  public List<StockDataDto> getLatestStockData() throws JsonProcessingException{
    return stockService.getLatestStockData();
  }

  @GetMapping("/latest-data/{group}")
  public List<StockDataDto> getLatestStockData(@PathVariable String group) throws JsonProcessingException{
    return stockService.getLatestStockData(group);
  }

}
