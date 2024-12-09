package com.bootcamp.bc_yahoo_finance.exception;

import com.bootcamp.bc_yahoo_finance.exception.code.StockQuoteErrorCode;
import com.bootcamp.bc_yahoo_finance.model.YHQuoteError;

public class StockQuoteYahooException extends StockException{
  public StockQuoteYahooException(YHQuoteError yhQuoteErrorDTO){
    super(StockQuoteErrorCode.QUOTE_ERROR, yhQuoteErrorDTO.toString());
  }
}
