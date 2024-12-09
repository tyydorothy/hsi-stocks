package com.bootcamp.bc_yahoo_finance.entity.comparators;

import java.util.Comparator;
import com.bootcamp.bc_yahoo_finance.entity.StockEntity;

public class StockSymbolComparator implements Comparator<StockEntity>{
  @Override
  public int compare(StockEntity s1, StockEntity s2) {
    return s1.getSymbol().compareTo(s2.getSymbol());
  }
}
