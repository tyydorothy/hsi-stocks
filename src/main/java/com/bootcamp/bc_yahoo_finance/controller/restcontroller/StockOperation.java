package com.bootcamp.bc_yahoo_finance.controller.restcontroller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.bootcamp.bc_yahoo_finance.dto.StockDataDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockOperation {

  @GetMapping("/latest-data/")
  List<StockDataDto> getLatestStockData() throws JsonProcessingException;

  @GetMapping("/latest-data/{group}")
  List<StockDataDto> getLatestStockData(@PathVariable String group) throws JsonProcessingException;



}
