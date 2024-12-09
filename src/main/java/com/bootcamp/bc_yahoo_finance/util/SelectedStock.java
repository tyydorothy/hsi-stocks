package com.bootcamp.bc_yahoo_finance.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bootcamp.bc_yahoo_finance.util.enums.StockGroup;



public class SelectedStock {

  private static final String[] FIN_STOCK = new String[] {"0005.HK","1299.HK","0939.HK","1398.HK","0388.HK","3988.HK","2318.HK","3968.HK","2388.HK","2628.HK","0011.HK"};
  private static final String[] TECH_STOCK = new String[] {"0700.HK","9988.HK","3690.HK","1810.HK","9618.HK","9999.HK","0992.HK","9888.HK","0981.HK","0285.HK"};
  private static final String[] PROP_STOCK = new String[] {"0016.HK","1109.HK","0823.HK","1113.HK","0688.HK","1997.HK","0012.HK","0960.HK","1209.HK","0101.HK","0017.HK"};

  public static final Map<StockGroup,String[]> SYMBOL_MAP = symbolMap();
  public static final List<String> SYMBOL_LIST = symbolList();

  private static Map<StockGroup,String[]> symbolMap(){

    Map<StockGroup,String[]> symbolMap = new HashMap<>();

    symbolMap.put(StockGroup.PROP, PROP_STOCK);
    symbolMap.put(StockGroup.TECH, TECH_STOCK);
    symbolMap.put(StockGroup.FIN, FIN_STOCK);

    return symbolMap;
  }

  private static List<String> symbolList(){
    return List.of(FIN_STOCK, TECH_STOCK, PROP_STOCK).stream()
      .flatMap(e -> Arrays.asList(e).stream().map(sym -> sym))
      .sorted(Comparator.naturalOrder())
      .toList();
  }

  public static void main(String[] args) {
    System.out.println(SelectedStock.SYMBOL_LIST);
  }

}
