package com.bootcamp.bc_yahoo_finance.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter // Json serialization
public class ErrorResponse{
  private int code;
  private String message;

}
