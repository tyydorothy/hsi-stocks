package com.bootcamp.bc_yahoo_finance.dto;

import com.bootcamp.bc_yahoo_finance.util.enums.StockGroup;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockInfoDto {
  private String symbol;
  private String shortName;
  private String longName;
  @Enumerated(EnumType.STRING)
  private StockGroup stockGroup; 

}
