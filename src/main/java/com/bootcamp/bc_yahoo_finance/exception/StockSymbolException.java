package com.bootcamp.bc_yahoo_finance.exception;

import com.bootcamp.bc_yahoo_finance.exception.code.StockSymbolErrorCode;

public class StockSymbolException extends StockException{
  
  public StockSymbolException(StockSymbolErrorCode e){
    super(e);
  }
}
