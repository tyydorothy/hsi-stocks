package com.bootcamp.bc_yahoo_finance.exception.code;

public enum StockServiceErrorCode implements StockErrorCode{

  ;

  private int code;
  private String message;
  
  private StockServiceErrorCode(int code, String message){
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
