package net.engineeringdigest.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {


    //when a method is present inside a class annotated with bean then the class shoulh have configuration annotation
    // and arguments present inside a method will automatically gets injected via SPRING
    //RedisConnectionFactory is an interface useful for creating and managing connection with redis server
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate= new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);

        //here we want that our key and value should be stored in String format which is default for CLI
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

    return redisTemplate;
    }
}
