package com.bootcamp.bc_yahoo_finance.exception;

import com.bootcamp.bc_yahoo_finance.exception.code.StockErrorCode;

public class StockException extends RuntimeException{
  private int code;
  
  public StockException(StockErrorCode e){
    super(e.getMessage());
    this.code = e.getCode();
  }

  public StockException(StockErrorCode e, String overrideMessage){
    super(overrideMessage);
    this.code = e.getCode();
  }

  public int getCode(){
    return this.code;
  }
}
