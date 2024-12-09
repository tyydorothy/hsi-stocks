package com.bootcamp.bc_yahoo_finance.exception.code;

public enum StockQuoteErrorCode implements StockErrorCode{
  QUOTE_ERROR(100,"")
  ;

  private int code;
  private String message;
  
  private StockQuoteErrorCode(int code, String message){
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
