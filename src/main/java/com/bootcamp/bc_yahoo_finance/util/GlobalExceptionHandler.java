package com.bootcamp.bc_yahoo_finance.util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bootcamp.bc_yahoo_finance.exception.StockSymbolException;
import com.bootcamp.bc_yahoo_finance.exception.StockException;

// Runtime Exception: unchecked exception

// throw -> catch by ExeptionHandler -> return Reponse/ResponseEntity

@RestControllerAdvice // @ResponseBody + @ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(StockSymbolException.class)
  //@ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse stockCodeExceptionHandler(StockSymbolException e) {
    return ErrorResponse.builder()//
        .code(e.getCode())//
        .message(e.getMessage()).build();
  }

  @ExceptionHandler(StockException.class)
  public ErrorResponse stockExceptionHandler(StockException e) {
    return ErrorResponse.builder().code(e.getCode()).message(e.getMessage())
        .build();
  }

  // @ExceptionHandler(RuntimeException.class)
  // public ErrorResponse RuntimeExceptionHandler(RuntimeException e) {
  //   return ErrorResponse.builder()//
  //       // .code(e.getCode())//
  //       .message(e.getMessage()).build();
  // }

  // // JsonProcessingException
  // @ExceptionHandler(Exception.class)
  // public ErrorResponse ExceptionHandler(Exception e) {
  //   return ErrorResponse.builder()//
  //       // .code(e.getCode())//
  //       .message(e.getMessage()).build();
 // }
}
