package com.bootcamp.bc_yahoo_finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import com.bootcamp.bc_yahoo_finance.util.RedisOperation;
import com.bootcamp.bc_yahoo_finance.util.yahoofinance.YHRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {
  
  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }

  @Bean
  public YHRestClient restClient(){
    return new YHRestClient();
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  RedisOperation redisOperation(RedisConnectionFactory redisConnectionFactory){
    return new RedisOperation(redisConnectionFactory);
  }

  @Bean
  ClassLoaderTemplateResolver templateResolver(){
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/"); 
    // cannot find folder if written: "/resources/templates" 
    // can omit this line actually coz it points to resources/templates
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("html");
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }

}