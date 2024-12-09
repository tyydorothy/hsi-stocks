package com.bootcamp.bc_yahoo_finance.service;

import java.util.List;
import com.bootcamp.bc_yahoo_finance.dto.StockDataDto;
import com.bootcamp.bc_yahoo_finance.dto.StockInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockService {

  List<StockInfoDto> getStockInfoList();

  List<StockDataDto> getLatestStockData() throws JsonProcessingException;

  List<StockDataDto> getLatestStockData(String group) throws JsonProcessingException;



}
