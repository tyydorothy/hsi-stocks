package com.bootcamp.bc_yahoo_finance.util;

import java.time.Duration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

// Notes
// No need to specify or create RedisConnectionFactory;
// Springboot automatically generate bean of RedisConnectionFactory // spring-boot-starter-data-redis

/*
 * this class is to perform redis commands
 */

// encapsulation
public class RedisOperation {

  private RedisTemplate<String,String> redisTemplate;

  public RedisOperation(RedisConnectionFactory redisConnectionFactory){
    RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    redisTemplate.setValueSerializer(RedisSerializer.json());
    redisTemplate.afterPropertiesSet();

    this.redisTemplate = redisTemplate;
  }

  public RedisTemplate<String,String> getRedisTemplate(){
    return this.redisTemplate;
  }

  public String get(String key){
    return this.redisTemplate.opsForValue().get(key);
  }

  public void set(String key, String value){
    this.redisTemplate.opsForValue().set(key, value);
  }

  public void set(String key, String value, Duration timeout){
    this.redisTemplate.opsForValue().set(key, value, timeout);
  }

  public void flushAll(){
    this.redisTemplate.execute( (RedisCallback<Void>) connection -> {
      connection.flushAll();
      return null;
    });
  }

}
