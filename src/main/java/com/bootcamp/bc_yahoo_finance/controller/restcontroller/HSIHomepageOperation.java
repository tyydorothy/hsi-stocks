package com.bootcamp.bc_yahoo_finance.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RequestMapping("/hsi-stocks")
public interface HSIHomepageOperation {
  

  @GetMapping("")
  String getOverview(Model model) throws JsonProcessingException;

  @GetMapping("/{group}")
  String getGroup(Model model, @PathVariable String group) throws JsonProcessingException;
  
  
}
