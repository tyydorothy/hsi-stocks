package com.bootcamp.bc_yahoo_finance.entity;

import com.bootcamp.bc_yahoo_finance.entity.comparators.StockSymbolComparator;
import com.bootcamp.bc_yahoo_finance.util.enums.StockGroup;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table (name = "tStock")

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class StockEntity extends StockSymbolComparator {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String symbol;
  private String shortName;
  private String longName;

  @Enumerated(EnumType.STRING)
  private StockGroup stockGroup; 

}
