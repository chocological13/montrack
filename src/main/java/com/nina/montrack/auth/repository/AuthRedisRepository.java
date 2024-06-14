package com.nina.montrack.auth.repository;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRedisRepository {

  private static final String STRING_KEY_PREFIX = "montrack:jwt:strings";
  private final ValueOperations<String, String> stringOps;

  public AuthRedisRepository(RedisTemplate<String, String> redisTemplate) {
    this.stringOps = redisTemplate.opsForValue();
  }

  public void saveJwtKey(String username, String jwtKey) {
    stringOps.set(STRING_KEY_PREFIX + username, jwtKey, 1, TimeUnit.HOURS);
  }

  public String getJwtKey(String username) {
    return stringOps.get(STRING_KEY_PREFIX + username);
  }

  public void removeJwtKey(String username) {
    stringOps.getOperations().delete(STRING_KEY_PREFIX + username);
  }
}
