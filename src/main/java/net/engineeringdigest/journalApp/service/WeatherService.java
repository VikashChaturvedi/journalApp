package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.Response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Value("${weather.apikey}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse =redisService.get("weather_of_"+ city,WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String finalAPI=appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY,apiKey).replace(Placeholders.CITY,city);
            //below we are hitting get request and deserializing the response json
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body!=null){
                redisService.set("weather_of_"+ city, body,300l);
            }
            return body;
        }
    }
}
