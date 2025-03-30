package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String apiKey="61a7b7271fbd49c488c85529253003";
    private String API="http://api.weatherapi.com/v1/current.json?key=KEY&q=CITY";

    public WeatherResponse getWeather(String city){
        String finalAPI=API.replace("KEY",apiKey).replace("CITY",city);
            //below we are hitting get request and deserializing the response json
         ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
         WeatherResponse body = response.getBody();
         return body;
    }
}
