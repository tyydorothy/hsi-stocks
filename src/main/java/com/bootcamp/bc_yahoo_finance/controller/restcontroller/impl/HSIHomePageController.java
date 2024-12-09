package com.bootcamp.bc_yahoo_finance.controller.restcontroller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.bootcamp.bc_yahoo_finance.controller.restcontroller.HSIHomepageOperation;
import com.bootcamp.bc_yahoo_finance.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class HSIHomePageController implements HSIHomepageOperation{
  
  @Autowired
  private StockService stockService;


  public String getOverview(Model model) throws JsonProcessingException{
      model.addAttribute("StockDataDto", stockService.getLatestStockData());
      return "homepage";
  }

  public String getGroup(Model model,String group) throws JsonProcessingException{

    switch(group){
      case "finance":
        model.addAttribute("StockDataDto", stockService.getLatestStockData("fin"));
        break;

      case "technology":
        model.addAttribute("StockDataDto", stockService.getLatestStockData("tech"));
        break;

      case "properties":
        model.addAttribute("StockDataDto", stockService.getLatestStockData("prop"));
        break;

    }
    
    return "homepage";
  }
  
}
