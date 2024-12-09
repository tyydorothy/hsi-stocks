package com.bootcamp.bc_yahoo_finance.exception.code;

import com.bootcamp.bc_yahoo_finance.util.SelectedStock;



public enum StockSymbolErrorCode implements StockErrorCode{
  STOCK_CODE_NOT_AVAIL(200,"Stock Code Not On List. Please refer to stock list:" + SelectedStock.SYMBOL_LIST),
  STOCK_CODE_INVALID_FORMAT(201,"Stock Code Format Invalid. Please enter 4-digit stock number with '.HK' at the end.")
  
  ;
  
  private int code;
  private String message;
  
  private StockSymbolErrorCode(int code, String message){
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode(){
    return this.code;
  }

  @Override
  public String getMessage(){
    return this.message;
  }

}
