package com.bootcamp.bc_yahoo_finance.entity.comparators;

import java.util.Comparator;
import org.springframework.stereotype.Component;
import com.bootcamp.bc_yahoo_finance.entity.StockDataEntity;

@Component
public class StockDataChronologicalComparator implements Comparator<StockDataEntity>{
  @Override
  public int compare(StockDataEntity e1, StockDataEntity e2) {
    if (e1.getRegularMarketTime().isBefore(e2.getRegularMarketTime()))
      return -1;

    if (e1.getRegularMarketTime().isAfter(e2.getRegularMarketTime()))
      return 1;

    return 0;
  }
}
