package com.gikim.doldolseo_msa_member.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void put(String key, String value, Long expTimeMin) {
        redisTemplate.opsForValue().set(key, value, expTimeMin, TimeUnit.MINUTES);
    }

    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean isExist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}
