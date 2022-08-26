package com.yuan.seckill.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @module:
 * @description: Redis配置类
 * @author: yuan_boss
 * @create: 2022-08-20 10:11
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash类型 key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash类型 value序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(redisConnectionFactory);


        return redisTemplate;
    }
    @Bean
    public DefaultRedisScript<Long> script(){
        DefaultRedisScript<Long> redisScrip = new DefaultRedisScript<>();
        //lock.lua脚本位置和application.yml同级目录
        redisScrip.setLocation(new ClassPathResource("stock.lua"));
        redisScrip.setResultType(Long.class);
        return redisScrip;

    }
}
