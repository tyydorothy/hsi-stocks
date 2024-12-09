package com.bootcamp.bc_yahoo_finance.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.bootcamp.bc_yahoo_finance.entity.comparators.StockDataChronologicalComparator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Entity or model is for DB/ an object being called.
// For non-RDBMS, implements serializable easier for debug
@Entity
@Table(name = "tStockData")

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StockDataEntity 
                  extends StockDataChronologicalComparator
                  implements Serializable{ 
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  @Setter
  private StockEntity stock;

  @Column (columnDefinition = "timestamp without time zone")
  @JsonSerialize (using = LocalDateTimeSerializer.class)
  @JsonDeserialize (using = LocalDateTimeDeserializer.class)
  private LocalDateTime regularMarketTime;
  private Double regularMarketPrice;
  private String bid;
  private Long bidSize;
  private String ask;
  private Long askSize;
  private Double regularMarketOpen;
  private Double regularMarketPreviousClose;
}
