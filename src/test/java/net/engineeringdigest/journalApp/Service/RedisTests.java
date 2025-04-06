package net.engineeringdigest.journalApp.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("gmail","vikash@gmail.com");
        Object mail =redisTemplate.opsForValue().get("gmail");
        Object salary= redisTemplate.opsForValue().get("salary");
        System.out.println(mail +"/n"+ salary);
        int a=1;
    }
}
