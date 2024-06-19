package com.nina.montrack.auth.repository;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRedisRepository {

  private static final String STRING_KEY_PREFIX = "montrack:jwt:strings:";
  private static final String BLACKLIST_PREFIX = ":blacklisted";
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

  // ? we are going to use blacklist instead
//  public void removeJwtKey(String username) {
//    stringOps.getOperations().delete(STRING_KEY_PREFIX + username);
//  }
  public void blacklistJwtKey(String username) {
    stringOps.set(STRING_KEY_PREFIX + username + ":blacklisted", "true", 1, TimeUnit.HOURS);
  }

  public boolean isValid (String username, String token) {
    String storedToken = stringOps.get(STRING_KEY_PREFIX + username);
    String blacklisted = stringOps.get(STRING_KEY_PREFIX + username + ":blacklisted");
    return storedToken != null && storedToken.equals(token) && blacklisted == null;
  }

  /* notes:
  * - when user logs in, save the key in redis.
  * - when user logs out, save another set with blacklist flag that will save the value of "true", this will have a
  * timeout of 15 mins after logging out
  * - validate token by checking if it exists, if it has the same token, and if blacklisted is null (meaning it the
  * flag is not set yet) */
}
