package com.bootcamp.bc_yahoo_finance.model;

import java.time.LocalDateTime;
import java.util.List;
import com.bootcamp.bc_yahoo_finance.util.jsonserializer.UnixToLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class YHQuote{
  private QuoteResponse quoteResponse;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public static class QuoteResponse{

  private List<Result> result;
  private String error;

  @Getter
  @ToString
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Result {

    private String language;
    private String region;
    private String quoteType;
    private String typeDisp;
    private String quoteSourceName;
    private Boolean triggerable;
    private String customPriceAlertConfidence;
    private String currency;
    private Boolean hasPrePostMarketData;
    private Long firstTradeDateMilliseconds;
    private Long priceHint;
    private String regularMarketChange;

    @JsonDeserialize(using = UnixToLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class) // delete for formal version
    private LocalDateTime regularMarketTime;
    private String regularMarketDayHigh;
    private String regularMarketDayRange;
    private String regularMarketChangePercent;
    private Double regularMarketPrice;
    private String exchange;
    private String shortName;
    private String longName;
    private String messageBoardId;
    private String exchangeTimezoneName;
    private String exchangeTimezoneShortName;
    private Long gmtOffSetMilliseconds;
    private String market;
    private Boolean esgPopulated;
    private String marketState;
    private String regularMarketDayLow;
    private Long regularMarketVolume;
    private String regularMarketPreviousClose;
    private String bid;
    private String ask;
    private Long bidSize;
    private Long askSize;
    private String fullExchangeName;
    private String financialCurrency;
    private String regularMarketOpen;
    private Boolean isEarningsDateEstimate; 
    private String trailingAnnualDividendRate;
    private String trailingPE;
    private String dividendRate;
    private String trailingAnnualDividendYield;
    private String dividendYield;
    private String priceEpsCurrentYear;
    private Long sharesOutstanding;
    private String bookValue;
    private Long marketCap;
    private String forwardPE;
    private String priceToBook;
    private Long sourceInterval;
    private Long exchangeDataDelayedBy;
    private String averageAnalystRating;
    private String symbol;
  }
}
}
