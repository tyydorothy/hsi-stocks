package com.bootcamp.bc_yahoo_finance.util.yahoofinance;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.bc_yahoo_finance.exception.StockQuoteYahooException;
import com.bootcamp.bc_yahoo_finance.model.YHQuote;
import com.bootcamp.bc_yahoo_finance.model.YHQuoteError;
import com.bootcamp.bc_yahoo_finance.util.enums.Scheme;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// requires new dependency: org.apache.hc.client5
public class YHRestClient {

  private static final String USER_AGENT = "Mozilla/5.0";
  private final Object lock = new Object();

  private RestTemplate restTemplate;
  private CrumbManager crumbManager;
  private BasicCookieStore cookieStore; // to store the cookie from cookie Manager

  public YHRestClient(){

    // empty cookieStore --> httpClient --> factory 
    this.cookieStore = new BasicCookieStore();

    CloseableHttpClient httpClient = HttpClients.custom()
      .setDefaultCookieStore(this.cookieStore)
      .build();

    HttpComponentsClientHttpRequestFactory factory = 
      new HttpComponentsClientHttpRequestFactory();
    factory.setHttpClient(httpClient);

    // user agent --> interceptor
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

    interceptors.add(new UserAgentInterceptor(USER_AGENT));

    // factory + interceptor --> restTemplate
    this.restTemplate = new RestTemplateBuilder()
      .setConnectTimeout(Duration.ofSeconds(10))
      .setReadTimeout(Duration.ofSeconds(10))
      .build();

    this.restTemplate.setRequestFactory(factory);
    this.restTemplate.setInterceptors(interceptors);

    // restTemplate --> CrumbManager --> CookieManager
    this.crumbManager = new CrumbManager(this.restTemplate);

  }


  public YHQuote getQuote(List<String> symbols) 
      throws JsonProcessingException{
    MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
    params.put("symbols", List.of(String.join(",",symbols)));
    params.put("crumb", List.of(""));
    String url = UriComponentsBuilder.newInstance()
        .scheme(Scheme.HTTPS.name().toLowerCase())
        .host(YhUriComponent.DOMAIN)
        .path(YhUriComponent.QUOTE_PATH)
        .queryParams(params)
        .build()
        .toUriString();

    // thread-safe problem
    synchronized(lock) {
      this.cookieStore.clear();
      String crumbKey = this.crumbManager.getCrumb();
      url = url.concat(crumbKey);
      System.out.println("url = " + url);

      ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
      if(!response.getStatusCode().equals(HttpStatus.OK)){
        YHQuoteError errorDTO = new ObjectMapper().readValue(response.getBody(), YHQuoteError.class);
        throw new StockQuoteYahooException(errorDTO);
      }
      return new ObjectMapper().readValue(response.getBody(), YHQuote.class);
    }
  }



  // ClientHttpRequestInterceptor is a functional interface
  private static class UserAgentInterceptor
      implements ClientHttpRequestInterceptor {
    private final String userAgent;

    public UserAgentInterceptor(String userAgent) {
      this.userAgent = userAgent;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
        ClientHttpRequestExecution execution) throws IOException {
      Objects.requireNonNull(request, "Request must not be null");
      Objects.requireNonNull(body, "Body must not be null");
      Objects.requireNonNull(execution, "Execution must not be null");
      request.getHeaders().set("User-Agent", userAgent);
      return execution.execute(request, body);
    }
  }


}
