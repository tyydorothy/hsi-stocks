package com.bootcamp.bc_yahoo_finance.util.yahoofinance;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.bc_yahoo_finance.util.enums.Scheme;

public class CrumbManager {
  private RestTemplate restTemplate;
  private CookieManager cookieManager;

  public CrumbManager(RestTemplate restTemplate){
    this.restTemplate = restTemplate;
    this.cookieManager = new CookieManager(restTemplate);
  }

  public String getCrumb(){
    try{
      String cookie = this.cookieManager.getCookie();
      HttpHeaders headers = new HttpHeaders();
      headers.add("Cookie",cookie);
      HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
      String crumbUrl = UriComponentsBuilder.newInstance()
        .scheme(Scheme.HTTPS.name().toLowerCase())
        .host(YhUriComponent.DOMAIN)
        .path(YhUriComponent.CRUMB_PATH)
        .toUriString();

      return restTemplate
        .exchange(crumbUrl, HttpMethod.GET,entity,String.class)
        .getBody();

    }catch(RestClientException e){
      
      // since the exception is only related to requesting the quote, not about the service we are providing
      // --> do try catch here and avoid throwing exception for handling.

      return null;
    }
  }

}
