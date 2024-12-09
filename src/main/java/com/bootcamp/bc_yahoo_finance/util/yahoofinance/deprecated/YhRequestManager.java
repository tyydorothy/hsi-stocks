package com.bootcamp.bc_yahoo_finance.util.yahoofinance.deprecated;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.bc_yahoo_finance.model.YHQuote;
import com.bootcamp.bc_yahoo_finance.util.enums.Scheme;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

// Notes
// need to use the same HttpClient for all methods, instead of creating new HttpClient each time
// yahoo can observe if the client is the same. 
// a new crumb will be given for a new client.
// no need to set cookies at Http Request headers when using the same HttpClient with CookiePolicy.ACCEPT_ALL
// coonectTimeout correspond to "Connection" part in request header; "keep-alive" in Postman
// can use RestTemplate to do the same thing


// revision
// static block cannot access instance methods
// static methods cannot access instance attributes

// get new cookie everytime you get the quote. coz cookie will expire


@Deprecated
@Component
public class YhRequestManager {

  public final String COOKIE_DOMAIN = "fc.yahoo.com";
  public final String DOMAIN     = "query1.finance.yahoo.com";
  public final String CRUMB_PATH = "/v1/test/getcrumb";
  public final String QUOTE_PATH = "/v7/finance/quote";

  public final String USER_AGENT = "Mozilla/5.0";

  public CookieManager cookieManager = new CookieManager();
  public HttpClient httpClient = this.httpClient();

  private ObjectMapper objectMapper;








  public HttpClient httpClient(){

    this.cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

    return HttpClient.newBuilder()
      .cookieHandler(cookieManager) // HttpHeaders: "Cookie"
      .followRedirects(Redirect.NORMAL) // not required for server to recognise you as "normal users"
      .connectTimeout(Duration.ofMinutes(5)) // HttpHeaders: "Connection"
      .build();

  }

  //@PostConstruct // to perform after dependency injection
  public List <String> getCookie(){

    String cookieUrl = UriComponentsBuilder.newInstance()
    .scheme(Scheme.HTTPS.name().toLowerCase())
    .host(COOKIE_DOMAIN)
    .toUriString();

    URI cookieUri = URI.create(cookieUrl);

    HttpRequest request = HttpRequest.newBuilder()
    .uri(cookieUri)
    .build();

    try{

      httpClient.send(request,BodyHandlers.ofString());

    }catch(IOException | InterruptedException e){

      System.out.println("Unable to get cookies. Request Failed.");

    }

    // Approach one
    // HttpResponse<String> response = httpClient.send(request,BodyHandlers.ofString());
    // HttpHeaders headers = response.headers();
    // List <String> cookies = Arrays.asList(headers.firstValue("set-cookie").get().split(";"))
    //   .stream()
    //   .map(str -> str.trim())
    //   .collect(Collectors.toList());

    // Approach two
    List <String> cookies = cookieManager.getCookieStore().getCookies()
      .stream()
      .map(c->c.toString())
      .collect(Collectors.toList());
    
    return cookies;
  }

  public String getCrumb() throws IOException, InterruptedException{

    URI crumbUri = URI.create(UriComponentsBuilder.newInstance()
      .scheme(Scheme.HTTPS.name().toLowerCase())
      .host(DOMAIN)
      .path(CRUMB_PATH)
      .toUriString());

    HttpRequest request = HttpRequest.newBuilder()
      .uri(crumbUri)
      .header("User-Agent", USER_AGENT)
      .build();

    HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

    return response.body();
    
  }

  public YHQuote getQuote(String stocks) throws IOException, InterruptedException{

    URI quoteUri = URI.create(UriComponentsBuilder.newInstance()
    .scheme(Scheme.HTTPS.name().toLowerCase())
    .host(DOMAIN)
    .path(QUOTE_PATH)
    .queryParam("symbols",stocks)
    .queryParam("crumb",this.getCrumb())
    .toUriString());

    HttpRequest request = HttpRequest.newBuilder()
      .uri(quoteUri)
      .header("User-Agent", USER_AGENT)
      .build();
    
    
    HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

    return this.objectMapper.readValue(response.body(),YHQuote.class);

  }


}
