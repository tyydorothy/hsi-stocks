package com.bootcamp.bc_yahoo_finance.controller.restcontroller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.bootcamp.bc_yahoo_finance.dto.StockDataDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockOperation {

  // @GetMapping("/stock-list")
  // List<String> getStockList() throws JsonProcessingException;

  // @GetMapping("/stock/{symbol}")
  // Boolean findStock(@PathVariable String symbol) throws  JsonProcessingException;

  @GetMapping("/latest-data/")
  List<StockDataDto> getLatestStockData() throws JsonProcessingException;

  @GetMapping("/latest-data/{group}")
  List<StockDataDto> getLatestStockData(@PathVariable String group) throws JsonProcessingException;

  // @GetMapping("/last-update-date")
  // String getLastMarketDate(@RequestParam String symbo);



}
