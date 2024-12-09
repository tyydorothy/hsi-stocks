package com.bootcamp.bc_yahoo_finance.util.yahoofinance;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.bc_yahoo_finance.util.enums.Scheme;

public class CookieManager {
  private RestTemplate restTemplate;

  public CookieManager(RestTemplate restTemplate){
    this.restTemplate = restTemplate;
  }

  public String getCookie(){
    try{

      String cookieUrl = UriComponentsBuilder.newInstance()
        .scheme(Scheme.HTTPS.name().toLowerCase())
        .host(YhUriComponent.COOKIE_DOMAIN)
        .toUriString();
      
      ResponseEntity<String> entity = 
        restTemplate.getForEntity(cookieUrl, String.class);
        List<String> cookies = entity.getHeaders().get("Set-Cookie");

      return cookies != null? cookies.get(0).split(";")[0] : null;

    } catch(RestClientException e){

      // still can get the cookies from fail response
      // to find what exception it will throw 
      // --> you catch the parent exception (e.g. Exception e, Runtime Exception ...)
      // --> sysout to check
      
      if(e instanceof HttpStatusCodeException){
        HttpHeaders headers = 
          ((HttpStatusCodeException) e).getResponseHeaders();
        if(headers != null){
          List<String> cookies = headers.get("Set-Cookie");
          return cookies != null? cookies.get(0).split(";")[0] : null;        }

      }

      return null;
    }
  }
}
