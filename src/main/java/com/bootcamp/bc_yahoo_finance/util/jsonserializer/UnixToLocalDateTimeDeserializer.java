package com.bootcamp.bc_yahoo_finance.util.jsonserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UnixToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime>{
  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {
    
    Long unixTime = Long.parseLong(p.getText());
      
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.ofOffset("GMT",ZoneOffset.ofHours(8)));
  }
}
